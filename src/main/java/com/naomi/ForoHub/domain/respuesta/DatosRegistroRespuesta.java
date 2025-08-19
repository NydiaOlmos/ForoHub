package com.naomi.ForoHub.domain.respuesta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroRespuesta(
        @NotBlank
        String mensaje,
        @NotNull
        @JsonAlias("id_topico")
        Long topico,
        @NotNull
        @JsonAlias("id_autor")
        Long autor
) {
}
