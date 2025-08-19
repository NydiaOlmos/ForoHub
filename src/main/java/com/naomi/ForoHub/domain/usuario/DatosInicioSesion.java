package com.naomi.ForoHub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosInicioSesion(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String contrasena
) {
}
