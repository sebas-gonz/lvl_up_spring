package com.lvlupspring.controller;

import com.lvlupspring.dto.CategoriaDTO;
import com.lvlupspring.entity.Categoria;
import com.lvlupspring.repository.CategoriaRepository;
import com.lvlupspring.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
@Tag(name = "Gestión de Categorías", description = "CRUD para crear, visualizar, modificar y eliminar categorías de productos.")
public class CategoriaController {
        private final CategoriaService categoriaService;

        @GetMapping
        @Operation(
                summary = "Listar todas las categorías",
                description = "Devuelve una lista de todas las categorías activas en el sistema, accesible por cualquier usuario."
        )
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Lista de categorías recuperada",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = Categoria.class))
                        )
                ),
                @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
        })
        public ResponseEntity<List<Categoria>> obtenerCategorias(){
            List<Categoria> categorias = categoriaService.obtenerCategorias();
            return ResponseEntity.ok(categorias);
        }

        @GetMapping("/{id}")
        @Operation(
                summary = "Obtener categoría por ID",
                description = "Busca una categoría específica utilizando su identificador único."
        )
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Categoría encontrada",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDTO.class))
                ),
                @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content)
        })
        public ResponseEntity<CategoriaDTO> obtenerCategoriaPorId(
                @Parameter(description = "ID de la categoría a buscar", example = "1")
                @PathVariable("id") Long id){
            CategoriaDTO categoria = categoriaService.categoriaPorId(id);
            return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
        }

        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping
        @Operation(
                summary = "Crear nueva categoría (ADMIN)",
                description = "Registra una nueva categoría de producto en el sistema. **Requiere rol de ADMINISTRADOR**."
        )
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Categoría creada exitosamente",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))
                ),
                @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
                @ApiResponse(responseCode = "403", description = "Acceso denegado (Rol insuficiente)", content = @Content)
        })
        public ResponseEntity<Categoria> crearCategoria(@RequestBody CategoriaDTO categoria){
            Categoria nuevaCategoria = categoriaService.crearCategoria(categoria);
            return ResponseEntity.status(201).body(nuevaCategoria);
        }

        @PreAuthorize("hasRole('ADMIN')")
        @PutMapping("/{id}")
        @Operation(
                summary = "Actualizar categoría por ID (ADMIN)",
                description = "Modifica los datos de una categoría existente. **Requiere rol de ADMINISTRADOR**."
        )
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Categoría actualizada exitosamente",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDTO.class))
                ),
                @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content),
                @ApiResponse(responseCode = "403", description = "Acceso denegado (Rol insuficiente)", content = @Content)
        })
        public ResponseEntity<CategoriaDTO> actualizarCategoria(
                @Parameter(description = "ID de la categoría a actualizar", example = "1")
                @PathVariable("id") Long id, @RequestBody CategoriaDTO dto) {
            CategoriaDTO categoriaActualizada = categoriaService.actualizarCategoria(id, dto);
            return categoriaActualizada != null ? ResponseEntity.ok(categoriaActualizada) : ResponseEntity.notFound().build();
        }

        @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/{id}")
        @Operation(
                summary = "Eliminar categoría por ID (ADMIN)",
                description = "Elimina una categoría del sistema (Puede ser una eliminación lógica o física). **Requiere rol de ADMINISTRADOR**."
        )
        @ApiResponses(value = {
                @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente (No Content)"),
                @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content),
                @ApiResponse(responseCode = "403", description = "Acceso denegado (Rol insuficiente)", content = @Content)
        })
        public ResponseEntity<Void> eliminarCategoria(
                @Parameter(description = "ID de la categoría a eliminar", example = "5")
                @PathVariable("id") Long id) {
            categoriaService.eliminarCategoria(id);
            return ResponseEntity.noContent().build();
        }

}
