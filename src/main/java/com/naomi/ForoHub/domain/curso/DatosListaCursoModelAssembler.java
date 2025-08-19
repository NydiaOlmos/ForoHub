package com.naomi.ForoHub.domain.curso;

import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class DatosListaCursoModelAssembler implements RepresentationModelAssembler<DatosRegistroCurso, EntityModel<DatosRegistroCurso>> {

    @Override
    @NonNull
    public EntityModel<DatosRegistroCurso> toModel(@NonNull DatosRegistroCurso entity) {
        return EntityModel.of(entity);
    }
}
