package com.naomi.ForoHub.domain.topico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizacionTopico(
        @NotNull
        Long id,
        String titulo,
        String mensaje,
        Long idUsuario,
        Long idCurso
) {
}
