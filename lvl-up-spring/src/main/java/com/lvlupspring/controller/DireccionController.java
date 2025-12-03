package com.lvlupspring.controller;

import com.lvlupspring.dto.DireccionDTO;
import com.lvlupspring.service.DireccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/direcciones")
@RequiredArgsConstructor
public class DireccionController {

    private final DireccionService direccionService;

    @GetMapping
    public ResponseEntity<List<DireccionDTO>> findDireccionesByUsuarioEmail(Authentication authentication) {
        String email = authentication.getName();
        List<DireccionDTO> direcciones = direccionService.findByUsuarioEmail(email);
        return ResponseEntity.ok(direcciones);
    }
    @PostMapping
    public ResponseEntity<DireccionDTO> crear(@RequestBody DireccionDTO dto, Authentication auth) {
        direccionService.crearDireccion(auth.getName(), dto);
        return ResponseEntity.ok(dto);
    }
}
