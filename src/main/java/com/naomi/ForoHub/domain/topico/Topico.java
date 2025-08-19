package com.naomi.ForoHub.domain.topico;


import com.naomi.ForoHub.domain.curso.Curso;
import com.naomi.ForoHub.domain.respuesta.Respuesta;
import com.naomi.ForoHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor")
    private Usuario autor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso")
    private Curso curso;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Respuesta> respuestas = new ArrayList<>();



    public Topico(DatosRegistroTopico datos, Usuario autor, Curso curso){
        this.id = null;
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.status = false;
        this.autor = autor;
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "Topico{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", status=" + status +
                ", autor=" + autor +
                ", curso=" + curso +
                '}';
    }

    public void actualizar(@Valid DatosActualizacionTopico datos, Usuario autor, Curso curso) {
        if (datos.titulo() != null) {
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }
        if (datos.idUsuario() != null) {
            this.autor = autor;
        }
        if (datos.idCurso() != null) {
            this.curso = curso;
        }
    }

    public void agregarRespuesta(Respuesta respuesta) {
        this.respuestas.add(respuesta);
    }
}
