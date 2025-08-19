package com.naomi.ForoHub.domain.respuesta;

import jakarta.validation.constraints.NotNull;

public record DatosSolucionTopico(
        @NotNull
        Long topico,
        @NotNull
        Long respuesta
) {
}
