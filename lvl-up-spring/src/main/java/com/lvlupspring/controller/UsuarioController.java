package com.lvlupspring.controller;

import com.lvlupspring.dto.UsuarioDTO;
import com.lvlupspring.entity.Usuario;
import com.lvlupspring.repository.UsuarioRepository;
import com.lvlupspring.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDTO> perfil(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(usuarioService.findByEmail(email));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable("id") Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/perfil")
    public ResponseEntity<UsuarioDTO> actualizarPerfil(
            @RequestBody UsuarioDTO usuarioDTO,
            Authentication authentication) {
        String usernameActual = authentication.getName();
        UsuarioDTO usuarioActualizado = usuarioService.actualizarPerfil(usuarioDTO,usernameActual);

        return ResponseEntity.ok(usuarioActualizado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteUsuarioByAdmin(@PathVariable("id") Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
