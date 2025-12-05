package com.lvlupspring.controller;

import com.lvlupspring.entity.Comuna;
import com.lvlupspring.service.ComunaService;
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
public class ComunaController {
    private final ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<Comuna>> findAll() {
        return ResponseEntity.ok(comunaService.findAll());
    }

    @GetMapping("/region/{regionId}")
    public ResponseEntity<List<Comuna>> findByRegionId(@PathVariable("regionId") Long regionId) {
        return ResponseEntity.ok(comunaService.findByRegionId(regionId));
    }

    @GetMapping("/{comunaId} ")
    public ResponseEntity<Comuna> findById(@PathVariable("comunaId") Long comunaId) {
        return ResponseEntity.ok(comunaService.findById(comunaId));
    }

    @GetMapping("/nombre/{nombreComuna}")
    public ResponseEntity<Comuna> findByNombreComuna(@PathVariable("nombreComuna") String nombreComuna) {
        return ResponseEntity.ok(comunaService.findByNombreComuna(nombreComuna));
    }
}
