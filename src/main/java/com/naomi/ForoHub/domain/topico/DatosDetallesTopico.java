package com.naomi.ForoHub.domain.topico;

import java.time.LocalDateTime;

public record DatosDetallesTopico(
        String titulo,
        String mensaje,
        LocalDateTime fecha,
        String estado,
        String autor,
        String curso
) {
    public DatosDetallesTopico(Topico topico, String status) {
        this(
            topico.getTitulo(),
            topico.getMensaje(),
            topico.getFechaCreacion(),
            status,
            topico.getAutor().getNombre(),
            topico.getCurso().getNombre()
        );
    }
}
