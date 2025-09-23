package co.com.pragma.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class CapacidadResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<Long> tecnologias;
}
