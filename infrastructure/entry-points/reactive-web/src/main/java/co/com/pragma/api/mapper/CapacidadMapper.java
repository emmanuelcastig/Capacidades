package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.CapacidadRequest;
import co.com.pragma.api.dto.CapacidadResponse;
import co.com.pragma.model.capacidad.Capacidad;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CapacidadMapper {
    Capacidad toDomain(CapacidadRequest request);
    CapacidadResponse toResponse(Capacidad domain);
}
