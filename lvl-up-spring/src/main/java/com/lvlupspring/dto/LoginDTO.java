package com.lvlupspring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Modelo de datos para la autenticación de usuarios")
public class LoginDTO {
    @Schema(
            description = "Correo electrónico del usuario registrado",
            example = "seba.gonzalez@duocuc.cl"
    )
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un formato de email válido")
    private String email;
    @Schema(
            description = "Contraseña del usuario",
            example = "Password123!"
    )
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
