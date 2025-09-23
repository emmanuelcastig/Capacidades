package co.com.pragma.model.capacidad;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TecnologiaResponse {
    private Long id;
    private String nombre;
}
