package co.com.pragma.r2dbc;

import co.com.pragma.r2dbc.entity.CapacidadEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MyReactiveRepository extends ReactiveCrudRepository<CapacidadEntity, Long>, ReactiveQueryByExampleExecutor<CapacidadEntity> {

}
