package com.naomi.ForoHub.domain.topico;

import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class DatosListaTopicoModelAssembler implements RepresentationModelAssembler<DatosDetallesTopico, EntityModel<DatosDetallesTopico>> {

    @Override
    @NonNull
    public EntityModel<DatosDetallesTopico> toModel(@NonNull DatosDetallesTopico datosDetallesTopico) {
        return EntityModel.of(datosDetallesTopico);
    }
}
