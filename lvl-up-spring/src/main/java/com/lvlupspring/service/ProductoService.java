package com.lvlupspring.service;

import com.lvlupspring.dto.ProductoDTO;
import com.lvlupspring.entity.Categoria;
import com.lvlupspring.entity.Producto;
import com.lvlupspring.repository.CategoriaRepository;
import com.lvlupspring.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    public Producto save(Producto p) {
        return productoRepository.save(p);
    }

    public Producto crearProducto(ProductoDTO dto){
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId()).orElse(null);
        if (categoria == null) {
            throw new IllegalArgumentException("Categoría no encontrada con ID: " + dto.getCategoriaId());
        }
        Producto producto = new Producto();
        producto.setNombreProducto(dto.getNombre());
        producto.setDescripcionProducto(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setOferta(dto.getOferta());
        producto.setPrecioOferta(dto.getPrecioOferta());
        producto.setStock(dto.getStock());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setImagenProducto(dto.getImagenUrl());
        producto.setCategoria(categoria);
        return save(producto);
    }

    public List<Producto> findByCategoriaId(Long categoriaId) {
        return productoRepository.findByCategoria_CategoriaId(categoriaId);
    }

    public  List<Producto> findByOfertaTrue() {
        return productoRepository.findByOfertaTrue();
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    public Producto actualizarProducto(Long id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId()).orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        producto.setNombreProducto(dto.getNombre());
        producto.setDescripcionProducto(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setStockMinimo(dto.getStockMinimo());
        producto.setOferta(dto.getOferta());
        producto.setPrecioOferta(dto.getPrecioOferta());
        producto.setImagenProducto(dto.getImagenUrl());
        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }
}
