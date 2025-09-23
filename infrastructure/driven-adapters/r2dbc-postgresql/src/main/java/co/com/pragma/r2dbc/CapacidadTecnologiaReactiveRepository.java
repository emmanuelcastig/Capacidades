package co.com.pragma.r2dbc;

import co.com.pragma.r2dbc.entity.CapacidadTecnologiasEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CapacidadTecnologiaReactiveRepository extends ReactiveCrudRepository<CapacidadTecnologiasEntity, Long>, ReactiveQueryByExampleExecutor<CapacidadTecnologiasEntity> {

}
