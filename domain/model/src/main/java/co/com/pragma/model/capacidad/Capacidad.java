package co.com.pragma.model.capacidad;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Capacidad {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<Long> tecnologias;
}
