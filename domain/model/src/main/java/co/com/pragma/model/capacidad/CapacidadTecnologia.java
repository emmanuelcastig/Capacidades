package co.com.pragma.model.capacidad;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CapacidadTecnologia {
    private Long id;
    private Long idCapacidad;
    private Long idTecnologia;
}
