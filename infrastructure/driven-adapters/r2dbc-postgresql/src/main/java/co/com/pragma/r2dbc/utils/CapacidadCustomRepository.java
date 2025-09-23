package co.com.pragma.r2dbc.utils;

import co.com.pragma.r2dbc.entity.CapacidadEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
@Slf4j
@Repository
@RequiredArgsConstructor
public class CapacidadCustomRepository {

    private final DatabaseClient databaseClient;

    public Flux<CapacidadEntity> findCapacidadesPaged(String sortBy, String order, int size, int offset) {
        String orderColumn;
        if ("nombre".equalsIgnoreCase(sortBy)) {
            orderColumn = "c.nombre";
        } else if ("tecnologias".equalsIgnoreCase(sortBy)) {
            orderColumn = "(SELECT COUNT(*) FROM capacidad_tecnologias ct WHERE ct.id_capacidad = c.id)";
        } else {
            orderColumn = "c.id";
        }

        String sql = String.format("""
            SELECT c.id, c.nombre, c.descripcion
            FROM capacidades c
            ORDER BY %s %s
            LIMIT %d OFFSET %d
            """, orderColumn, order.equalsIgnoreCase("desc") ? "DESC" : "ASC", size, offset);
        log.info("Sql generado: {}", sql);

        return databaseClient.sql(sql)
                .map((row, metadata) -> new CapacidadEntity(
                        row.get("id", Long.class),
                        row.get("nombre", String.class),
                        row.get("descripcion", String.class)
                ))
                .all();
    }
}