package co.com.pragma.model.capacidad.consumer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TecnologiasRestConsumer {
    Flux<Tecnologia> listarTecnologias();
    Mono<Void> eliminarTecnologiaHuerfana(Long id);
}
