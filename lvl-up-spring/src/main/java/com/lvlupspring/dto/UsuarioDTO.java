package com.lvlupspring.dto;

import com.lvlupspring.entity.Rol;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioDTO {
    private Long usuarioId;
    private String nombre;
    private String apellido;
    private String email;
    private String fotoUsuario;

    private LocalDate fechaNacimiento;
    private Integer runUsuario;
    private String dvUsuario;
    private String rol;
}
