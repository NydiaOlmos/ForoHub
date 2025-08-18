package com.naomi.ForoHub.domain.respuesta;

import com.naomi.ForoHub.domain.topico.Topico;
import com.naomi.ForoHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @ManyToOne(fetch = FetchType.EAGER)
    private Topico topico;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario autor;
    private Boolean solucion;
}
