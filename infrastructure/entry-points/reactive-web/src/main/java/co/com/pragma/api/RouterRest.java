package co.com.pragma.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/v1/capacidades"), handler::crearCapacidad)
                .andRoute(GET("/api/v1/capacidades/paginadas"), handler::listarCapacidadesPaginadas)
                .andRoute(GET("/api/v1/capacidades"), handler::listarCapacidades)
                .andRoute(DELETE("/api/v1/capacidades/{id}"), handler::eliminarCapacidad);
    }
}
