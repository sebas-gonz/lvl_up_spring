package com.lvlupspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false,unique = true)
    private Long usuarioId;

    @Size(min = 1, max = 100,message = "El nombre debe tener entre 1 y 100 caracteres")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Size(min = 1, max = 200,message = "El apellido debe tener entre 1 y 200 caracteres")
    @NotBlank(message = "El apellido no puede estar vacío")
    @Column(nullable = false, length = 200)
    private String apellido;

    @Email(message = "El correo debe ser válido")
    @NotBlank(message = "El correo no puede estar vacío")
    @Size(max = 250, message = "El correo no puede tener más de 250 caracteres")
    @Column(nullable = false, length = 250, unique = true)
    private String email;

    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    @Column(nullable = false)
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El run no puede ser nulo")
    @Range(min = 1000000, max = 99999999, message = "El run debe estar entre 1.000.000 y 99.999.999")
    @Column(nullable = false, length = 8, unique = true)
    private Integer runUsuario;

    @NotBlank(message = "El dígito verificador no puede estar vacío")
    @Size(min = 1, max = 1, message = "El dígito verificador debe tener 1 carácter")
    @Column(nullable = false, length = 1)
    private String dvUsuario;

    @Column(columnDefinition = "TEXT",nullable = true, length = 500)
    private String fotoUsuario;

    @NotEmpty(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(nullable = false, length = 255)
    private String password;

    @CreatedDate
    @Column(updatable=false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Direccion> direcciones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Boleta> boletas;

}
