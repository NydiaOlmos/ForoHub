package com.naomi.ForoHub.controller;

import com.naomi.ForoHub.domain.curso.DatosRegistroCurso;
import com.naomi.ForoHub.domain.respuesta.*;
import com.naomi.ForoHub.domain.topico.Topico;
import com.naomi.ForoHub.domain.topico.TopicoRepository;
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
@RequestMapping("/respuesta")
public class RespuestaController {
    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private PagedResourcesAssembler<DatosDetalleRespuesta> pagedResourcesAssembler;

    @Autowired
    private DatosListaRespuestaModelAssembler datosListaRespuestaModelAssembler;

    @Transactional
    @PostMapping
    public ResponseEntity registroRespuesta(@RequestBody @Valid DatosRegistroRespuesta datos, UriComponentsBuilder uriBuilder) {
        var topico = topicoRepository.getReferenceById(datos.topico());
        var autor = usuarioRepository.getReferenceById(datos.autor());
        var respuesta = new Respuesta(datos.mensaje(), topico, autor);
        respuestaRepository.save(respuesta);
        var uri = uriBuilder.path("/respuesta/").buildAndExpand().toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleRespuesta(respuesta));
    }

    @Transactional
    @PutMapping
    public ResponseEntity actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datos) {
        var respuesta = respuestaRepository.getReferenceById(datos.id());
        Topico topico = null;
        Usuario autor = null;
        if (datos.autor() != null) {
            autor = usuarioRepository.getReferenceById(datos.autor());
        }
        if (datos.topico() != null) {
            topico = topicoRepository.getReferenceById(datos.topico());
        }
        respuesta.actualizar(datos.mensaje(), topico, autor);
        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarRespuesta(@PathVariable Long id) {
        var respuesta = respuestaRepository.getReferenceById(id);
        if (respuesta.getTopico().getStatus()) {
            respuesta.getTopico().setStatus(false);
        }
        respuestaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleRespuesta>>> listaRepuestasGeneral(@PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {
        Page<DatosDetalleRespuesta> pagina = respuestaRepository.findAll(paginacion).map(DatosDetalleRespuesta::new);
        var page = pagedResourcesAssembler.toModel(pagina, datosListaRespuestaModelAssembler);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagedModel<EntityModel<DatosDetalleRespuesta>>> listaRepuestasTopico(@PathVariable Long id, @PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {
        Page<DatosDetalleRespuesta> pagina = respuestaRepository.findByTopicoIdOrderBySolucionDesc(id,paginacion).map(DatosDetalleRespuesta::new);
        var page = pagedResourcesAssembler.toModel(pagina, datosListaRespuestaModelAssembler);
        return ResponseEntity.ok(page);
    }

    @Transactional
    @PutMapping("/solucion")
    public ResponseEntity solucionarTopico(@RequestBody @Valid DatosSolucionTopico datos) {
        var topico = topicoRepository.getReferenceById(datos.topico());
        var respuesta = respuestaRepository.getReferenceById(datos.respuesta());
        topico.setStatus(true);
        respuesta.setSolucion(true);
        return ResponseEntity.ok("Se solucionó el tópico.");
    }
}
