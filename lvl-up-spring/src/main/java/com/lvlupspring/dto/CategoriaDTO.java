package com.lvlupspring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoriaDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombreCategoria;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255)
    private String descripcionCategoria;

    @NotBlank(message = "La imagen es obligatoria")
    private String imagenCategoria;

    private String prefijoCategoria;
}
