package co.com.pragma.consumer;

import co.com.pragma.model.capacidad.consumer.Tecnologia;
import co.com.pragma.model.capacidad.consumer.TecnologiasRestConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestConsumer implements TecnologiasRestConsumer {
    private final WebClient client;

    @Override
    public Flux<Tecnologia> listarTecnologias() {
        return client
                .get()
                .uri("/api/v1/tecnologia")
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new RuntimeException("Error servidor: " + response.statusCode())))
                .bodyToFlux(Tecnologia.class);
    }

    @Override
    public Mono<Void> eliminarTecnologiaHuerfana(Long id) {
        log.info("eliminando tecnologia huerfana " + id);
        return client
                .delete()
                .uri("/api/v1/tecnologia/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(new RuntimeException("Error servidor: " + response.statusCode())))
                .bodyToMono(Void.class);
    }
}
