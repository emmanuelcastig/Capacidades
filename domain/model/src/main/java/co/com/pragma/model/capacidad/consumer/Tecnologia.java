package co.com.pragma.model.capacidad.consumer;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Tecnologia {
    private Long id;
    private String nombre;
    private String descripcion;
}
