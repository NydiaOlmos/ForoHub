package com.naomi.ForoHub.controller;

import com.naomi.ForoHub.domain.curso.Curso;
import com.naomi.ForoHub.domain.curso.CursoRepository;
import com.naomi.ForoHub.domain.topico.*;
import com.naomi.ForoHub.domain.usuario.Usuario;
import com.naomi.ForoHub.domain.usuario.UsuarioRepository;
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
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private PagedResourcesAssembler<DatosDetallesTopico> pagedResourcesAssembler;
    @Autowired
    private DatosListaTopicoModelAssembler datosListaTopicoModelAssembler;

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    @PostMapping
    public ResponseEntity registro(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriBuilder) {
        System.out.println(datos);
        var autor = usuarioRepository.getReferenceById(datos.idUsuario());
        var curso = cursoRepository.getReferenceById(datos.idCurso());
        var topico = new Topico(datos, autor, curso);
        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetallesTopico(topico, generadorEstado(topico.getStatus())));
    }

    private String generadorEstado(Boolean status) {
        if(status){
            return "Resuelto";
        } else  {
            return "Abierto";
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity mostrarTopico(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetallesTopico(topico, generadorEstado(topico.getStatus())));
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<DatosDetallesTopico>>> listaTopico(@PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {
        Page<DatosDetallesTopico> pagina = topicoRepository.findAllOrderByStatusFalse(paginacion).map(t -> new DatosDetallesTopico(t, generadorEstado(t.getStatus())));
        var page = pagedResourcesAssembler.toModel(pagina, datosListaTopicoModelAssembler);
        return ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping
    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizacionTopico datos) {
        System.out.println(datos);
        var topico = topicoRepository.getReferenceById(datos.id());
        Usuario autor = null;
        Curso curso = null;
        if(datos.idUsuario() != null){
            autor = usuarioRepository.getReferenceById(datos.idUsuario());
        }
        if (datos.idCurso() != null) {
            curso = cursoRepository.getReferenceById(datos.idCurso());
        }
        topico.actualizar(datos, autor, curso);
        return ResponseEntity.ok(new DatosDetallesTopico(topico, generadorEstado(topico.getStatus())));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
