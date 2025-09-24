package co.com.pragma.r2dbc;

import co.com.pragma.r2dbc.entity.CapacidadTecnologiasEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CapacidadTecnologiaReactiveRepository extends ReactiveCrudRepository<CapacidadTecnologiasEntity, Long>, ReactiveQueryByExampleExecutor<CapacidadTecnologiasEntity> {
    Flux<CapacidadTecnologiasEntity> findAllByIdTecnologia(Long idTecnologia);
}
