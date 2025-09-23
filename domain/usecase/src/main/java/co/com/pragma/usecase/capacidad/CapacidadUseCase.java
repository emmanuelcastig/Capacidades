package co.com.pragma.usecase.capacidad;

import co.com.pragma.model.capacidad.Capacidad;
import co.com.pragma.model.capacidad.Tecnologia;
import co.com.pragma.model.capacidad.consumer.TecnologiasRestConsumer;
import co.com.pragma.model.capacidad.gateways.CapacidadRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.HashSet;
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
}
