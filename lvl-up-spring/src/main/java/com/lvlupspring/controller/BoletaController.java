package com.lvlupspring.controller;

import com.lvlupspring.dto.BoletaDTO;
import com.lvlupspring.entity.Boleta;
import com.lvlupspring.service.BoletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boletas")
@RequiredArgsConstructor
@Tag(name = "Gestión de Boletas", description = "Endpoints para procesar compras, listar historial y ver detalles de transacciones.")
public class BoletaController {

    private final BoletaService boletaService;


    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener detalle de una boleta",
            description = "Busca una boleta específica por su ID único para mostrar el detalle de productos comprados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Boleta encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boleta.class))
            ),
            @ApiResponse(responseCode = "404", description = "Boleta no encontrada", content = @Content)
    })
    public ResponseEntity<Boleta> getBoletaById(@PathVariable("id") Long id){
        Boleta boleta = boletaService.findById(id);

        return boleta != null ? ResponseEntity.ok(boleta) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Listar todas las boletas (Admin)",
            description = "Obtiene un registro completo de todas las ventas históricas del sistema. **Requiere rol de ADMINISTRADOR**."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado recuperado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Boleta.class))
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Acceso denegado: El usuario no tiene rol de Administrador", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    })
    @GetMapping
    public  ResponseEntity<List<Boleta>> getAllBoletas(){
        List<Boleta> boletas = boletaService.findAll();
        return ResponseEntity.ok(boletas);
    }

    @PostMapping
    @Operation(
            summary = "Generar una nueva boleta de compra",
            description = "Recibe los datos del carrito y el usuario para crear una orden de compra, descontar stock y generar el registro."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Boleta creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boleta.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud (Stock insuficiente o datos inválidos)", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuario no autenticado", content = @Content)
    })
    public ResponseEntity<Boleta> crearBoleta(@RequestBody BoletaDTO dto, Authentication authentication){
        String emailUsuario = authentication.getName();
        Boleta nuevaBoleta = boletaService.crearBoleta(dto,emailUsuario);
        return ResponseEntity.ok(nuevaBoleta);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(
            summary = "Obtener historial de compras de un usuario",
            description = "Devuelve una lista de todas las boletas asociadas al ID de un usuario específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial recuperado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado o sin compras", content = @Content)
    })
    public ResponseEntity<List<Boleta>> obtenerHistorial(@PathVariable Long usuarioId) {
        List<Boleta> historial = boletaService.obtenerHistorialUsuario(usuarioId);
        return ResponseEntity.ok(historial);
    }
}
