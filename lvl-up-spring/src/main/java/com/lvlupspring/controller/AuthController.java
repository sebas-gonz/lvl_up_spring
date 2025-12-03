package com.lvlupspring.controller;

import com.lvlupspring.dto.LoginDTO;
import com.lvlupspring.dto.RegistroUsuarioDTO;
import com.lvlupspring.service.AuthService;
import com.lvlupspring.validator.UsuarioValidator;
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
public class AuthController {
    private  final AuthService authService;
    private final UsuarioValidator usuarioValidator;


    @PostMapping("/registro")
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
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            return ResponseEntity.ok(authService.login(loginDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }

}
