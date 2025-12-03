package com.lvlupspring.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Data // Genera Getters, Setters, toString, etc.
public class RegistroUsuarioDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 200, message = "El apellido no puede tener más de 200 caracteres")
    private String apellido;

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

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "Debes confirmar la contraseña")
    private String passwordConfirmation;
}