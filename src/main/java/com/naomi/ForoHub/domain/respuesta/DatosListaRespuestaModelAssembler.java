package com.naomi.ForoHub.domain.respuesta;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class DatosListaRespuestaModelAssembler implements RepresentationModelAssembler<DatosDetalleRespuesta, EntityModel<DatosDetalleRespuesta>> {
    @Override
    public EntityModel<DatosDetalleRespuesta> toModel(DatosDetalleRespuesta entity) {
        return EntityModel.of(entity);
    }
}
