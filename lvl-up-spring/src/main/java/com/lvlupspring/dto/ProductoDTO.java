package com.lvlupspring.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductoDTO {
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(nullable = false, length = 500, columnDefinition = "TEXT")
    @Size(max = 500, message = "La descripción del producto no puede tener más de 500 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    private Integer precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 1, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "Debes indicar si está en oferta")
    private Boolean oferta;

    private Integer precioOferta;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    private String imagenUrl;

    @NotNull(message = "El stock mínimo es obligatorio")
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Integer stockMinimo;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;
}
