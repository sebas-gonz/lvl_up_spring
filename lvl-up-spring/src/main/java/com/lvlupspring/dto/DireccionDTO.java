package com.lvlupspring.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DireccionDTO {
    @NotBlank(message = "La calle es obligatoria")
    private String calle;
    private Integer numeroDepto;
    private String indicaciones;
    @NotBlank(message = "La comuna es obligatoria")
    private Long comuna;
}
