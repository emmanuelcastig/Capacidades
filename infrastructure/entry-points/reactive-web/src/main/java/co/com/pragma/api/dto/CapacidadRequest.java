package co.com.pragma.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CapacidadRequest {

    @NotBlank(message = "El nombre es obligatorio y no puede estar vacío")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria y no puede estar vacía")
    private String descripcion;

    @NotEmpty(message = "Debe seleccionar al menos 3 tecnologías")
    @Size(min = 3, max = 20, message = "Debe seleccionar entre 3 y 20 tecnologías")
    private List<Long> tecnologias;
}
