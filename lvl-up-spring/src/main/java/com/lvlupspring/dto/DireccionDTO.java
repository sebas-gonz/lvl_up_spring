package com.lvlupspring.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DireccionDTO {
    @NotBlank(message = "La calle es obligatoria")
    private String calle;
    @NotBlank(message = "El número de casa es obligatorio")
    private String numero;
    private Integer numeroDepto;
    private String indicaciones;
    @NotBlank(message = "La comuna es obligatoria")
    private String comuna;
}
