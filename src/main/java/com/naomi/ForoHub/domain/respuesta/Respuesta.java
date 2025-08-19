package com.naomi.ForoHub.domain.respuesta;

import com.naomi.ForoHub.domain.topico.Topico;
import com.naomi.ForoHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Repuesta")
@Table(name = "respuestas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico")
    private Topico topico;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor")
    private Usuario autor;
    @Setter
    private Boolean solucion;

    public Respuesta(String mensaje, Topico topico, Usuario usuario) {
        this.id = null;
        this.mensaje = mensaje;
        this.topico = topico;
        this.fechaCreacion = LocalDateTime.now();
        this.autor = usuario;
        this.solucion = false;
    }

    public void actualizar(String mensaje, Topico topico, Usuario usuario) {
        if (mensaje != null) {
            this.mensaje = mensaje;
        }
        if (topico != null) {
            this.topico = topico;
        }
        if (usuario != null) {
            this.autor = usuario;
        }
    }
}
