package com.lvlupspring.controller;

import com.lvlupspring.entity.Region;
import com.lvlupspring.service.RegionService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/regiones")
@RequiredArgsConstructor
@Tag(name = "Gestión de Ubicaciones (Regiones)", description = "Endpoints para consultar las regiones disponibles.")
public class RegionController {
    private  final RegionService regionService;

    @GetMapping
    @Operation(
            summary = "Listar todas las regiones",
            description = "Obtiene el listado completo de las regiones del país. **Usar para poblar el primer selector del formulario de dirección**."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de regiones recuperada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Region.class))
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    public ResponseEntity<List<Region>> findAll() {
        return ResponseEntity.ok(regionService.findAll());
    }

    @GetMapping("/{regionId}")
    @Operation(
            summary = "Obtener región por ID",
            description = "Busca una región específica mediante su identificador numérico."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Región encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Region.class))
            ),
            @ApiResponse(responseCode = "404", description = "Región no encontrada", content = @Content)
    })
    public ResponseEntity<Region> findById(
            @Parameter(description = "ID único de la región", example = "7")
            @PathVariable("regionId") Long regionId) {
        return ResponseEntity.ok(regionService.findById(regionId));
    }
}
