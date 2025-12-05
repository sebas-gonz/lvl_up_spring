package com.lvlupspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "boleta")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false,unique = true)
    private Long boletaId;

    @NotNull
    private Integer totalBoleta;

    @NotBlank(message = "La calle no puede estar vacía")
    @Column(nullable = false, length = 255)
    @Size(max = 255,min=10, message = "La calle debe tener entre 10 y 255 caracteres")
    private String calle;

    @Column(nullable = true)
    private Integer numeroDepto;

    @Column(nullable = true, length = 255)
    @Size(max = 255, message = "Las indicaciones no puede tener más de 255 caracteres")
    private String indicaciones;

    @NotBlank(message = "La comuna no puede estar vacía")
    @Column(nullable = false)
    private String comuna;

    @CreatedDate
    @Column(updatable=false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties(value = {"direcciones", "boletas", "password", "roles", "hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    @OneToMany(mappedBy = "boleta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleBoleta> detalles;
}
