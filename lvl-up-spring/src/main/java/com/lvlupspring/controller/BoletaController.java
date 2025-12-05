package com.lvlupspring.controller;

import com.lvlupspring.dto.BoletaDTO;
import com.lvlupspring.entity.Boleta;
import com.lvlupspring.service.BoletaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boletas")
@RequiredArgsConstructor
public class BoletaController {

    private final BoletaService boletaService;


    @GetMapping("/{id}")
    public ResponseEntity<Boleta> getBoletaById(@PathVariable("id") Long id){
        Boleta boleta = boletaService.findById(id);

        return boleta != null ? ResponseEntity.ok(boleta) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public  ResponseEntity<List<Boleta>> getAllBoletas(){
        List<Boleta> boletas = boletaService.findAll();
        return ResponseEntity.ok(boletas);
    }

    @PostMapping
    public ResponseEntity<Boleta> crearBoleta(@RequestBody BoletaDTO dto, Authentication authentication){
        String emailUsuario = authentication.getName();
        Boleta nuevaBoleta = boletaService.crearBoleta(dto,emailUsuario);
        return ResponseEntity.ok(nuevaBoleta);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Boleta>> obtenerHistorial(@PathVariable Long usuarioId) {
        List<Boleta> historial = boletaService.obtenerHistorialUsuario(usuarioId);
        return ResponseEntity.ok(historial);
    }
}
