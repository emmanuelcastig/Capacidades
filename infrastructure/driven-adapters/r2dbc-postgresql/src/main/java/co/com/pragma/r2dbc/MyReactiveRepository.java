package co.com.pragma.r2dbc;

import co.com.pragma.r2dbc.entity.CapacidadEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MyReactiveRepository extends ReactiveCrudRepository<CapacidadEntity, Long>, ReactiveQueryByExampleExecutor<CapacidadEntity> {

    @Query("""
        SELECT ct.id_tecnologia 
        FROM capacidad_tecnologias ct
        WHERE ct.id_capacidad = :capacidadId
        """)
    Flux<Long> findTecnologiasByCapacidad(Long capacidadId);
}
