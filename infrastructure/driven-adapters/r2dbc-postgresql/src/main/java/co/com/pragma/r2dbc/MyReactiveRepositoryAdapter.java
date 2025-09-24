package co.com.pragma.r2dbc;

import co.com.pragma.model.capacidad.Capacidad;
import co.com.pragma.model.capacidad.gateways.CapacidadRepository;
import co.com.pragma.r2dbc.entity.CapacidadEntity;
import co.com.pragma.r2dbc.entity.CapacidadTecnologiasEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import co.com.pragma.r2dbc.utils.CapacidadCustomRepository;
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
    private final CapacidadCustomRepository capacidadCustomRepository;

    public MyReactiveRepositoryAdapter(
            MyReactiveRepository repository,
            CapacidadTecnologiaReactiveRepository capacidadTecnologiaRepository,
            CapacidadCustomRepository capacidadCustomRepository,
            ObjectMapper mapper
    ) {
        super(repository, mapper, d -> mapper.map(d, Capacidad.class));
        this.capacidadTecnologiaRepository = capacidadTecnologiaRepository;
        this.capacidadCustomRepository = capacidadCustomRepository;
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
    public Flux<Capacidad> obtenerCapacidadesPaginadas(int page, int size, String sortBy, String order) {
        int offset = page * size;

        return capacidadCustomRepository.findCapacidadesPaged(sortBy, order, size, offset)
                .concatMap(entity ->
                        repository.findTecnologiasByCapacidad(entity.getId())
                                .collectList()
                                .map(tecnologias -> Capacidad.builder()
                                        .id(entity.getId())
                                        .nombre(entity.getNombre())
                                        .descripcion(entity.getDescripcion())
                                        .tecnologias(tecnologias)
                                        .build()
                                )
                );
    }

    @Override
    public Flux<Capacidad> obtenerTodasLasCapacidades() {
        return repository.findAll()
                .concatMap(entity ->
                        repository.findTecnologiasByCapacidad(entity.getId())
                                .collectList()
                                .map(tecnologias -> Capacidad.builder()
                                        .id(entity.getId())
                                        .nombre(entity.getNombre())
                                        .descripcion(entity.getDescripcion())
                                        .tecnologias(tecnologias)
                                        .build()
                                )
                );
    }
}
