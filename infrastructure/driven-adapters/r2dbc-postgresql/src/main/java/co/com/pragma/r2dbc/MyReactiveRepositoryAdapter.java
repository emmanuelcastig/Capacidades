package co.com.pragma.r2dbc;

import co.com.pragma.model.capacidad.Capacidad;
import co.com.pragma.model.capacidad.gateways.CapacidadRepository;
import co.com.pragma.r2dbc.entity.CapacidadEntity;
import co.com.pragma.r2dbc.entity.CapacidadTecnologiasEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Capacidad,
        CapacidadEntity,
    Long,
    MyReactiveRepository
> implements CapacidadRepository {
    private final CapacidadTecnologiaReactiveRepository capacidadTecnologiaRepository;

    public MyReactiveRepositoryAdapter(
            MyReactiveRepository repository,
            CapacidadTecnologiaReactiveRepository capacidadTecnologiaRepository,
            ObjectMapper mapper
    ) {
        super(repository, mapper, d -> mapper.map(d, Capacidad.class));
        this.capacidadTecnologiaRepository = capacidadTecnologiaRepository;
    }


    public Mono<Void> guardarCapacidad(Capacidad capacidad) {
        CapacidadEntity entity = new CapacidadEntity(null, capacidad.getNombre(), capacidad.getDescripcion());

        return repository.save(entity)
                .flatMap(savedEntity -> {
                    // 2. Guardar relaciones en capacidad_tecnologias
                    return Flux.fromIterable(capacidad.getTecnologias())
                            .map(idTecnologia -> CapacidadTecnologiasEntity.builder()
                                    .idCapacidad(savedEntity.getId())
                                    .idTecnologia(idTecnologia)
                                    .build())
                            .collectList()
                            .flatMapMany(capacidadTecnologias ->
                                    capacidadTecnologiaRepository.saveAll(capacidadTecnologias)
                            )
                            .then();
                });
    }

    @Override
    public Flux<Capacidad> obtenerCapacidades() {
        return null;
    }
}
