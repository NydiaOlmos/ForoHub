package com.naomi.ForoHub.domain.respuesta;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosActualizarRespuesta(
        Long id,
        String mensaje,
        @JsonAlias("id_topico")
        Long topico,
        @JsonAlias("id_autor")
        Long autor
) {
}
