package com.naomi.ForoHub.controller;

import com.naomi.ForoHub.domain.rol.RolRepository;
import com.naomi.ForoHub.domain.usuario.*;
import com.naomi.ForoHub.infra.security.DatosTokenJWT;
import com.naomi.ForoHub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PagedResourcesAssembler<DatosDetallesUsuario> pagedResourcesAssembler;

    @Autowired
    private DatosListaUsuarioModelAssembler datosListaUsuarioModelAssembler;

    @PostMapping("/inicioSesion")
    public ResponseEntity iniciarSesion(@Valid @RequestBody DatosInicioSesion datos) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(datos.email(), datos.contrasena());
        var authenticacion = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.generarToken((Usuario) authenticacion.getPrincipal());
        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }

    @Transactional
    @PostMapping("/registro")
    public ResponseEntity registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datos, UriComponentsBuilder uriBuilder) {
        var rol = rolRepository.getReferenceById("USER");
        var usuario = new Usuario(datos, rol);
        usuario.setContrasena(encodePassword(datos.contrasena()));
        usuarioRepository.save(usuario);
        var uri = uriBuilder.path("/login/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetallesUsuario(usuario));
    }

    @Transactional
    @PutMapping
    public ResponseEntity actualizarUsuario(@RequestBody @Valid DatosActualizacionUsuario datos) {
        var usuario = usuarioRepository.findById(datos.id());
        if (usuario.isPresent()) {
            usuario.get().actualizar(datos);
            if (datos.contasena() != null) {
                usuario.get().setContrasena(encodePassword(datos.contasena()));
            }
            return ResponseEntity.ok(new DatosDetallesUsuario(usuario.get()));
        }
        throw new RuntimeException("El usuario no existe en la db");
    }

    @GetMapping("/{id}")
    public ResponseEntity mostrarUsuario(@PathVariable Long id) {
        var usuario = usuarioRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetallesUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<DatosDetallesUsuario>>> listaUsuarios(@PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion) {
        Page<DatosDetallesUsuario> pagina = usuarioRepository.findAll(paginacion).map(DatosDetallesUsuario::new);
        var page = pagedResourcesAssembler.toModel(pagina, datosListaUsuarioModelAssembler);
        return ResponseEntity.ok(page);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
