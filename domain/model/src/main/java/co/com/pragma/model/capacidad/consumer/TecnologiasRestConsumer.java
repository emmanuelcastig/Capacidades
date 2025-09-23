package co.com.pragma.model.capacidad.consumer;

import co.com.pragma.model.capacidad.Tecnologia;
import reactor.core.publisher.Flux;

public interface TecnologiasRestConsumer {
    Flux<Tecnologia> listarTecnologias();
}
