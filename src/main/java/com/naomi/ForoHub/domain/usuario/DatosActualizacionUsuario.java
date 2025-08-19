package com.naomi.ForoHub.domain.usuario;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DatosActualizacionUsuario (
    @NotNull
    Long id,
    String nombre,
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$",
        message = "La contraseña debe tener al menos 8 caracteres, una mayúscula y un número"
    )
    String contasena
) {}
