package com.lvlupspring.controller;

import com.lvlupspring.entity.Comuna;
import com.lvlupspring.service.ComunaService;
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
@RequestMapping("/comunas")
@RequiredArgsConstructor
@Tag(name = "Gestión de Ubicaciones (Comunas)", description = "Endpoints para listar comunas disponibles")
public class ComunaController {
    private final ComunaService comunaService;

    @GetMapping
    @Operation(
            summary = "Listar todas las comunas",
            description = "Devuelve el listado completo de comunas."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado recuperado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Comuna.class))
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    public ResponseEntity<List<Comuna>> findAll() {
        return ResponseEntity.ok(comunaService.findAll());
    }

    @GetMapping("/region/{regionId}")
    @Operation(
            summary = "Listar comunas por Región",
            description = "Obtiene todas las comunas pertenecientes a una región específica (identificada por su ID numérico)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de comunas de la región recuperada",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comuna.class)))
            ),
            @ApiResponse(responseCode = "404", description = "Región no encontrada o sin comunas", content = @Content)
    })
    public ResponseEntity<List<Comuna>> findByRegionId(
            @Parameter(description = "ID numérico de la región (ej: 13 para RM)", example = "13")
            @PathVariable("regionId") Long regionId) {
        return ResponseEntity.ok(comunaService.findByRegionId(regionId));
    }

    @GetMapping("/{comunaId} ")
    @Operation(
            summary = "Obtener comuna por ID",
            description = "Busca los datos de una comuna específica mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comuna encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comuna.class))
            ),
            @ApiResponse(responseCode = "404", description = "Comuna no encontrada", content = @Content)
    })
    public ResponseEntity<Comuna> findById(
            @Parameter(description = "ID único de la comuna", example = "1301")
            @PathVariable("comunaId") Long comunaId) {
        return ResponseEntity.ok(comunaService.findById(comunaId));
    }

    @GetMapping("/nombre/{nombreComuna}")
    @Operation(
            summary = "Buscar comuna por nombre",
            description = "Busca una comuna específica utilizando su nombre exacto."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comuna encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comuna.class))
            ),
            @ApiResponse(responseCode = "404", description = "Comuna no encontrada", content = @Content)
    })
    public ResponseEntity<Comuna> findByNombreComuna(@PathVariable("nombreComuna") String nombreComuna) {
        return ResponseEntity.ok(comunaService.findByNombreComuna(nombreComuna));
    }
}
