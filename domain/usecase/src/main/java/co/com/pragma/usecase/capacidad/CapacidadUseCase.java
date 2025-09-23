package co.com.pragma.usecase.capacidad;

import co.com.pragma.model.capacidad.Capacidad;
import co.com.pragma.model.capacidad.CapacidadResponse;
import co.com.pragma.model.capacidad.Tecnologia;
import co.com.pragma.model.capacidad.TecnologiaResponse;
import co.com.pragma.model.capacidad.consumer.TecnologiasRestConsumer;
import co.com.pragma.model.capacidad.gateways.CapacidadRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CapacidadUseCase {

    private final CapacidadRepository capacidadRepository;
    private final TecnologiasRestConsumer tecnologiasConsumer;

    public Mono<Void> guardarCapacidad(Capacidad capacidad) {

        return tecnologiasConsumer.listarTecnologias().collectList()
                .flatMap(tecnologiasDisponibles -> {

                    Set<Long> idsDisponibles = tecnologiasDisponibles.stream()
                            .map(Tecnologia::getId)
                            .collect(Collectors.toSet());

                    if (!idsDisponibles.containsAll(capacidad.getTecnologias())) {
                        return Mono.error(new IllegalArgumentException(
                                "Existen tecnologias asociadas que no están registradas en el sistema"));
                    }

                    Set<Long> tecnologiasUnicas = new HashSet<>(capacidad.getTecnologias());
                    if (tecnologiasUnicas.size() != capacidad.getTecnologias().size()) {
                        return Mono.error(new IllegalArgumentException("Una capacidad no puede tener tecnologias repetidas"));
                    }

                    return capacidadRepository.guardarCapacidad(capacidad);
                });
    }

    public Flux<CapacidadResponse> obtenerCapacidades(int page, int size, String sortBy, String order) {
        return capacidadRepository.obtenerCapacidades(page, size, sortBy, order)
                .flatMap(capacidad ->
                        tecnologiasConsumer.listarTecnologias().collectMap(Tecnologia::getId, Tecnologia::getNombre)
                                .map(map -> {
                                    List<TecnologiaResponse> tecnologias = capacidad.getTecnologias().stream()
                                            .map(id -> new TecnologiaResponse(id, map.get(id)))
                                            .toList();

                                    return CapacidadResponse.builder()
                                            .id(capacidad.getId())
                                            .nombre(capacidad.getNombre())
                                            .descripcion(capacidad.getDescripcion())
                                            .tecnologias(tecnologias)
                                            .build();
                                })
                );
    }
}
