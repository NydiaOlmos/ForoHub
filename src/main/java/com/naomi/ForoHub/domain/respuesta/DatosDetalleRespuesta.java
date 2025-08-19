package com.naomi.ForoHub.domain.respuesta;

import java.time.LocalDateTime;

public record DatosDetalleRespuesta(
        String mensaje,
        Long idTopico,
        LocalDateTime fechaCreacion,
        Long idAutor,
        Boolean solucion
) {
    public DatosDetalleRespuesta (Respuesta respuesta) {
        this(
                respuesta.getMensaje(),
                respuesta.getTopico().getId(),
                respuesta.getFechaCreacion(),
                respuesta.getAutor().getId(),
                respuesta.getSolucion()
        );
    }
}
