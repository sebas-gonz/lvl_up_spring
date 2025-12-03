package com.lvlupspring.controller;

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
        public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable("id") Long id){
            Categoria categoria = categoriaService.categoriaPorId(id);
            return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
        }

        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping
        public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria){
            Categoria nuevaCategoria = categoriaService.crearCategoria(categoria);
            return ResponseEntity.status(201).body(nuevaCategoria);
        }

        @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarCategoria(@PathVariable("id") Long id) {
            categoriaService.eliminarCategoria(id);
            return ResponseEntity.noContent().build();
        }

}
