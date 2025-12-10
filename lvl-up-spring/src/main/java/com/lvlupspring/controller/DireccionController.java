package com.lvlupspring.controller;

import com.lvlupspring.dto.DireccionDTO;
import com.lvlupspring.entity.Direccion;
import com.lvlupspring.service.DireccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/direcciones")
@RequiredArgsConstructor
@Tag(name = "Gestión de Direcciones", description = "Endpoints para que los usuarios registren y administren sus lugares de despacho.")
public class DireccionController {

    private final DireccionService direccionService;

    @GetMapping
    @Operation(
            summary = "Listar direcciones de un usuario",
            description = "Recupera todas las direcciones registradas por un cliente específico para mostrarlas en el selector de despacho."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de direcciones recuperada",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Direccion.class))
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    public ResponseEntity<List<DireccionDTO>> findDireccionesByUsuarioEmail(Authentication authentication) {
        String email = authentication.getName();
        List<DireccionDTO> direcciones = direccionService.findByUsuarioEmail(email);
        return ResponseEntity.ok(direcciones);
    }
    @PostMapping
    @Operation(
            summary = "Registrar nueva dirección",
            description = "Guarda una nueva dirección de despacho asociada a un usuario."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Dirección guardada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Direccion.class))
            ),
            @ApiResponse(responseCode = "400", description = "Datos de dirección inválidos", content = @Content)
    })
    public ResponseEntity<DireccionDTO> crear(@RequestBody DireccionDTO dto, Authentication auth) {
        direccionService.crearDireccion(auth.getName(), dto);
        return ResponseEntity.ok(dto);
    }
}
