package com.naomi.ForoHub.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroCurso(
        @NotBlank
        String nombre,
        @NotBlank
        String categoria
) {
    public DatosRegistroCurso(Curso curso) {
        this(
                curso.getNombre(),
                curso.getCategoria()
        );
    }
}
