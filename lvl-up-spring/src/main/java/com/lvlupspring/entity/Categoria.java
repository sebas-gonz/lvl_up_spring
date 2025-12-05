package com.lvlupspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "categoria")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false,unique = true)
    private Long categoriaId;

    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String nombreCategoria;

    @NotBlank(message = "La descripción de la categoría no puede estar vacío")
    @Column(nullable = false, length = 255)
    private String descripcionCategoria;

    @NotBlank(message = "La imagen de la categoría no puede estar vacío")
    @Column(nullable = false, length = 500, columnDefinition = "TEXT")
    private String imagenCategoria;

    @NotBlank(message = "El prefijo de la categoría no puede estar vacío")
    @Column(nullable = false, length = 10)
    private String prefijoCategoria;

    @CreatedDate
    @Column(updatable=false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoria")
    @JsonManagedReference
    private List<Producto> productos;
}
