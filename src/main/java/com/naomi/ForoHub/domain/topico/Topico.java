package com.naomi.ForoHub.domain.topico;


import com.naomi.ForoHub.domain.curso.Curso;
import com.naomi.ForoHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    private Boolean status;
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario autor;
    @ManyToOne(fetch = FetchType.EAGER)
    private Curso curso;
}
