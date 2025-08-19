package com.naomi.ForoHub.domain.usuario;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class DatosListaUsuarioModelAssembler implements RepresentationModelAssembler<DatosDetallesUsuario, EntityModel<DatosDetallesUsuario>> {
    @Override
    public EntityModel<DatosDetallesUsuario> toModel(DatosDetallesUsuario entity) {
        return EntityModel.of(entity);
    }
}
