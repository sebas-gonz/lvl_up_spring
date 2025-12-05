package com.lvlupspring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "producto")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false,unique = true)
    private Long productoId;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Column(nullable = false, length = 150)
    @Size(max = 150, message = "El nombre del producto no puede tener más de 150 caracteres")
    private String nombreProducto;

    @NotBlank(message = "La descripción del producto no puede estar vacía")
    @Column(nullable = false, length = 500, columnDefinition = "TEXT")
    @Size(max = 500, message = "La descripción del producto no puede tener más de 500 caracteres")
    private String descripcionProducto;

    @NotNull(message = "El precio del producto no puede ser nulo")
    private Integer precio;

    @NotNull(message = "El estado de oferta no puede ser nulo")
    @Column(nullable = false)
    private Boolean oferta = false;

    @Column(nullable = true)
    private Integer precioOferta;

    @NotNull(message = "El stock no puede ser nulo")
    @Column(nullable = false)
    private Integer stock;

    @NotNull(message = "El stock mínimo no puede ser nulo")
    @Column(nullable = false)
    private Integer stockMinimo;

    @NotBlank(message = "La imagen del producto no puede estar vacía")
    @Column(nullable = true, columnDefinition = "TEXT", length = 500)
    private String imagenProducto;

    @CreatedDate
    @Column(updatable=false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "producto")
    @JsonIgnore
    private List<DetalleBoleta> detalleBoletas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @JsonIgnoreProperties({"productos", "hibernateLazyInitializer", "handler"})
    private Categoria categoria;
}
