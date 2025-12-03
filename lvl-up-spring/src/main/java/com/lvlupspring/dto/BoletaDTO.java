package com.lvlupspring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BoletaDTO {
    @NotNull(message = "Debes seleccionar una dirección de despacho")
    private Long direccionId;

    @NotEmpty(message = "El carrito no puede estar vacío")
    private List<DetalleDTO> items;
}
