package co.com.pragma.api;

import co.com.pragma.api.dto.CapacidadRequest;
import co.com.pragma.api.mapper.CapacidadMapper;
import co.com.pragma.model.capacidad.CapacidadResponse;
import co.com.pragma.usecase.capacidad.CapacidadUseCase;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import jakarta.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {

    private final CapacidadUseCase capacidadUseCase;
    private final Validator validator;
    private final TransactionalOperator transactionalOperator;
    private final CapacidadMapper capacidadMapper;

    public Mono<ServerResponse> crearCapacidad(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CapacidadRequest.class)
                .flatMap(this::validacion)
                .map(capacidadMapper::toDomain)
                .as(transactionalOperator::transactional)
                .flatMap(capacidadUseCase::guardarCapacidad)
                .then(ServerResponse.status(HttpStatus.CREATED).build())
                .onErrorResume(ValidationException.class, e ->
                        ServerResponse.badRequest().bodyValue(e.getMessage())
                );
    }

    public Mono<ServerResponse> listarCapacidades(ServerRequest serverRequest) {
        int page = Integer.parseInt(serverRequest.queryParam("page").orElse("0"));
        int size = Integer.parseInt(serverRequest.queryParam("size").orElse("10"));
        String sortBy = serverRequest.queryParam("sortBy").orElse("nombre");
        String order = serverRequest.queryParam("order").orElse("asc");
        log.info("Parametros de paginacion - page: {}, size: {}, sortBy: {}, order: {}", page, size, sortBy, order);
        return ServerResponse.ok()
                .body(
                        capacidadUseCase.obtenerCapacidades(page, size, sortBy, order),
                        CapacidadResponse.class
                );
    }
    public Mono<CapacidadRequest> validacion(CapacidadRequest request) {
        Set<ConstraintViolation<CapacidadRequest>> violaciones = validator.validate(request);
        if (!violaciones.isEmpty()) {
            String errorMessage = violaciones.stream()
                    .map(violation -> violation.getPropertyPath() + ": " +
                            violation.getMessage())
                    .collect(Collectors.joining(", "));
            return Mono.error(new ValidationException(errorMessage));
        }
        return Mono.just(request);
    }

}
