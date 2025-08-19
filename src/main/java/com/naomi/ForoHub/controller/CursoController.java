package com.naomi.ForoHub.controller;

import com.naomi.ForoHub.domain.curso.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/curso")

@SecurityRequirement(name = "bearer-key")
public class CursoController {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private PagedResourcesAssembler<DatosRegistroCurso> pagedResourcesAssembler;

    @Autowired
    private DatosListaCursoModelAssembler datosListaCursoModelAssembler;

    @Transactional
    @PostMapping
    public ResponseEntity registroCurso(@RequestBody @Valid DatosRegistroCurso datos, UriComponentsBuilder uriBuilder) {
        var curso = new Curso(datos);
        cursoRepository.save(curso);
        var uri = uriBuilder.path("/curso/{id}").buildAndExpand(curso.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosRegistroCurso(curso));
    }

    @GetMapping("/{id}")
    public ResponseEntity mostrarCurso(@PathVariable Long id){
        var curso = cursoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRegistroCurso(curso));
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<DatosRegistroCurso>>> listaCurso(@PageableDefault(size = 10, sort = {"nombre"})Pageable paginacion) {
        Page<DatosRegistroCurso> pagina = cursoRepository.findAll(paginacion).map(DatosRegistroCurso::new);
        var page = pagedResourcesAssembler.toModel(pagina, datosListaCursoModelAssembler);
        return ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping
    public ResponseEntity actualizarCurso(@RequestBody @Valid DatosActualizacionCurso datos){
        var curso = cursoRepository.getReferenceById(datos.id());
        curso.actualizar(datos);
        return ResponseEntity.ok(new DatosRegistroCurso(curso));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarCurso(@PathVariable Long id) {
        cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
