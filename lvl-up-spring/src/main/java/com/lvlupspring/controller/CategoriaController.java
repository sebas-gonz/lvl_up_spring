package com.lvlupspring.controller;

import com.lvlupspring.dto.CategoriaDTO;
import com.lvlupspring.entity.Categoria;
import com.lvlupspring.repository.CategoriaRepository;
import com.lvlupspring.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {
        private final CategoriaService categoriaService;

        @GetMapping
        public ResponseEntity<List<Categoria>> obtenerCategorias(){
            List<Categoria> categorias = categoriaService.obtenerCategorias();
            return ResponseEntity.ok(categorias);
        }

        @GetMapping("/{id}")
        public ResponseEntity<CategoriaDTO> obtenerCategoriaPorId(@PathVariable("id") Long id){
            CategoriaDTO categoria = categoriaService.categoriaPorId(id);
            return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
        }

        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping
        public ResponseEntity<Categoria> crearCategoria(@RequestBody CategoriaDTO categoria){
            Categoria nuevaCategoria = categoriaService.crearCategoria(categoria);
            return ResponseEntity.status(201).body(nuevaCategoria);
        }

        @PreAuthorize("hasRole('ADMIN')")
        @PutMapping("/{id}")
        public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable("id") Long id, @RequestBody CategoriaDTO dto) {
            CategoriaDTO categoriaActualizada = categoriaService.actualizarCategoria(id, dto);
            return categoriaActualizada != null ? ResponseEntity.ok(categoriaActualizada) : ResponseEntity.notFound().build();
        }

        @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarCategoria(@PathVariable("id") Long id) {
            categoriaService.eliminarCategoria(id);
            return ResponseEntity.noContent().build();
        }

}
