package co.com.pragma.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("capacidad_tecnologias")
public class CapacidadTecnologiasEntity {
    @Id
    private Long id;
    private Long idCapacidad;
    private Long idTecnologia;
}
