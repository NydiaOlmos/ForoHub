package com.naomi.ForoHub.controller;

import com.naomi.ForoHub.domain.usuario.DatosInicioSesion;
import com.naomi.ForoHub.domain.usuario.Usuario;
import com.naomi.ForoHub.infra.security.DatosTokenJWT;
import com.naomi.ForoHub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UsuarioController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping("/inicioSesion")
    public ResponseEntity iniciarSesion(@Valid @RequestBody DatosInicioSesion datos) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(datos.email(), datos.contrasena());
        var authenticacion = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.generarToken((Usuario) authenticacion.getPrincipal());
        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }
}
