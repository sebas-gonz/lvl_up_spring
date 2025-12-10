package com.lvlupspring.controller;

import com.lvlupspring.dto.ProductoDTO;
import com.lvlupspring.entity.Producto;
import com.lvlupspring.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
@Tag(name = "Catálogo de Productos", description = "Endpoints para gestionar el inventario, realizar búsquedas y filtrar productos.")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    @Operation(summary = "Listar todos los productos", description = "Obtiene el catálogo completo de productos disponibles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Catálogo recuperado",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Producto.class))))
    })
    public ResponseEntity<List<Producto>> findAll() {

        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/ofertas" )
    @Operation(
            summary = "Listar productos en oferta",
            description = "Filtra y devuelve únicamente los productos que están marcados como 'Oferta' en la base de datos."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de ofertas recuperada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Producto.class))
                    )
            )
    })
    public ResponseEntity<List<Producto>> findOfertas() {
        return ResponseEntity.ok(productoService.findByOfertaTrue());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Crear nuevo producto (ADMIN)", description = "Agrega un nuevo ítem al inventario. **Requiere rol de ADMINISTRADOR**.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado (No es Admin)", content = @Content)
    })
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody ProductoDTO dto) {
        Producto nuevoProducto = productoService.crearProducto(dto);
        return ResponseEntity.status(201).body(nuevoProducto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar producto (ADMIN)", description = "Elimina un producto del catálogo. **Requiere rol de ADMINISTRADOR**.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado (No Content)"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content)
    })
    public ResponseEntity<Void> deleteProducto(
            @Parameter(description = "ID del producto a eliminar", example = "10")
            @PathVariable("id") Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Muestra el detalle completo de un producto específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    public ResponseEntity<Producto> getProductoById(
            @Parameter(description = "ID único del producto", example = "10")
            @PathVariable("id") Long id) {
        Producto producto = productoService.findById(id);
        return producto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(producto);
    }

    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Filtrar productos por Categoría", description = "Obtiene todos los productos que pertenecen a una categoría específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos filtrados recuperados",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Producto.class))))
    })
    public ResponseEntity<List<Producto>> getProductosByCategoria(
            @Parameter(description = "ID de la categoría", example = "2")
            @PathVariable("categoriaId") Long categoriaId) {
        List<Producto> productos = productoService.findByCategoriaId(categoriaId);
        return ResponseEntity.ok(productos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto (ADMIN)", description = "Modifica datos (precio, stock, descripción) de un producto existente. Requiere rol de ADMINISTRADOR.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content)
    })
    public ResponseEntity<Producto> actualizarProducto(
            @Parameter(description = "ID del producto a editar", example = "10")
            @PathVariable("id") Long id,
            @Valid @RequestBody ProductoDTO dto) {
        Producto productoExistente = productoService.findById(id);
        return productoExistente == null ? ResponseEntity.notFound().build() :  ResponseEntity.ok(productoService.actualizarProducto(id, dto));
    }

}
