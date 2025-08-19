package com.naomi.ForoHub.domain.curso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Curso")
@Table(name = "cursos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String categoria;

    public Curso(DatosRegistroCurso datos) {
        this.id = null;
        this.nombre = datos.nombre();
        this.categoria = datos.categoria();
    }

    public void actualizar(DatosActualizacionCurso datos) {
        if (datos.categoria() != null) {
            this.categoria = datos.categoria();
        }
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
    }
}
