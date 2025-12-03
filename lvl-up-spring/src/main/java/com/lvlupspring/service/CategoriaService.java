package com.lvlupspring.service;

import com.lvlupspring.entity.Categoria;
import com.lvlupspring.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public Categoria categoriaPorId(Long id){
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria crearCategoria(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    public void eliminarCategoria(Long id){
        categoriaRepository.deleteById(id);
    }

    public List<Categoria> obtenerCategorias(){
        return categoriaRepository.findAll();
    }
}
