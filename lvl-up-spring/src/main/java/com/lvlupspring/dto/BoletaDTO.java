package com.lvlupspring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BoletaDTO {
    private Integer totalBoleta;
    private String calle;
    private Integer numeroDepto;
    private String indicaciones;
    private String comuna;
    private List<DetalleDTO> detalles;
}
