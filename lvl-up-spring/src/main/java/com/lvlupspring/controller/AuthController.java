package com.lvlupspring.controller;

import com.lvlupspring.dto.LoginDTO;
import com.lvlupspring.dto.RegistroUsuarioDTO;
import com.lvlupspring.service.AuthService;
import com.lvlupspring.validator.UsuarioValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para gestión de sesiones y registro de usuarios")
public class AuthController {
    private  final AuthService authService;
    private final UsuarioValidator usuarioValidator;


    @PostMapping("/registro")
    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Crea una nueva cuenta de usuario en el sistema con rol por defecto."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de registro inválidos o correo ya existente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> registrar(@Valid @RequestBody RegistroUsuarioDTO registroDTO, BindingResult result) {
        usuarioValidator.validate(registroDTO, result);
        if (result.hasErrors()){
            List<String> errores = result.getFieldErrors()
                    .stream().map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok(authService.registrar(registroDTO));
    }

    @PostMapping("/login")
    @Operation(
            summary = "Iniciar Sesión de Usuario",
            description = "Autentica al usuario mediante correo y contraseña y devuelve un token JWT."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login exitoso",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"token\":\"eyJhbGci...\"}"))
            ),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            return ResponseEntity.ok(authService.login(loginDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }

}
