package com.lvlupspring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Data
@Schema(description = "Modelo de datos para registrar un nuevo usuario en el sistema")
public class RegistroUsuarioDTO {
    @Schema(
            description = "Nombre completo del usuario",
            example = "Sebastian Gonzalez"
    )
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;
    @Schema(
            description = "Correo electrónico (debe ser único en el sistema)",
            example = "seba.gonzalez@duocuc.cl"
    )
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 200, message = "El apellido no puede tener más de 200 caracteres")
    private String apellido;

    @Schema(
            description = "Contraseña segura para la cuenta",
            example = "PasswordSeguro123!"
    )
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debes ingresar un correo válido")
    @Size(max = 250)
    private String email;


    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El RUN es obligatorio")
    @Range(min = 1000000, max = 99999999, message = "El RUN debe ser válido (sin dígito verificador)")
    private Integer runUsuario;

    @NotBlank(message = "El dígito verificador es obligatorio")
    @Pattern(regexp = "^[0-9kK]$", message = "El DV debe ser un número o 'K'")
    @Size(max = 1)
    private String dvUsuario;

    private String fotoUsuario;

    @Schema(
            description = "Contraseña para el acceso. Se recomienda al menos 6 caracteres.",
            example = "Seguridad2024!",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "Debes confirmar la contraseña")
    private String passwordConfirmation;

    @Schema(
            description = "Rol asignado al usuario (Por defecto suele ser USER o CLIENTE).",
            example = "1"
    )
    private Long rolId;
}