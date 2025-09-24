package co.com.pragma.model.capacidad.gateways;

import co.com.pragma.model.capacidad.Capacidad;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CapacidadRepository {
    Mono<Void> guardarCapacidad(Capacidad capacidad);
    Flux<Capacidad> obtenerCapacidadesPaginadas(int page, int size, String sortBy, String order);
    Flux<Capacidad> obtenerTodasLasCapacidades();
    Flux<Long> eliminarCapacidad(Long id);
}
