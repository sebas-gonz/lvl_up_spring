package com.lvlupspring.controller;

import com.lvlupspring.dto.UsuarioDTO;
import com.lvlupspring.entity.Usuario;
import com.lvlupspring.repository.UsuarioRepository;
import com.lvlupspring.service.UsuarioService;
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
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Gestión de Usuarios", description = "Administración de cuentas de usuario.")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/perfil")
    @Operation(
            summary = "Obtener perfil de usuario",
            description = "Busca los datos de un usuario específico por autenticacion."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))
            ),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    public ResponseEntity<UsuarioDTO> perfil(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(usuarioService.findByEmail(email));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(
            summary = "Listar todos los usuarios (ADMIN)",
            description = "Obtiene el listado completo de usuarios registrados en el sistema. Requiere rol de ADMINISTRADOR."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de usuarios recuperado",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Usuario.class)))
            ),
            @ApiResponse(responseCode = "403", description = "Acceso denegado (No es Admin)", content = @Content)
    })
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PutMapping("/perfil")
    @Operation(
            summary = "Actualizar usuario",
            description = "Modifica los datos de un usuario existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    public ResponseEntity<UsuarioDTO> actualizarPerfil(
            @RequestBody UsuarioDTO usuarioDTO,
            Authentication authentication) {
        String usernameActual = authentication.getName();
        UsuarioDTO usuarioActualizado = usuarioService.actualizarPerfil(usuarioDTO,usernameActual);

        return ResponseEntity.ok(usuarioActualizado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/{id}")
    @Operation(
            summary = "Eliminar usuario (ADMIN)",
            description = "Elimina permanentemente una cuenta de usuario. Requiere rol de administrador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    public ResponseEntity<Void> deleteUsuarioByAdmin(@PathVariable("id") Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
