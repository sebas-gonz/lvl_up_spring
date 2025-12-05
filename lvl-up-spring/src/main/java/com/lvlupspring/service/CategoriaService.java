package com.lvlupspring.service;

import com.lvlupspring.dto.CategoriaDTO;
import com.lvlupspring.entity.Categoria;
import com.lvlupspring.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaDTO categoriaPorId(Long id){
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        return categoria != null ? convertToDTO(categoria) : null;
    }

    public Categoria crearCategoria(CategoriaDTO dto){
        Categoria categoria = new Categoria();
        categoria.setNombreCategoria(dto.getNombreCategoria());
        categoria.setDescripcionCategoria(dto.getDescripcionCategoria());
        categoria.setImagenCategoria(dto.getImagenCategoria());
        categoria.setPrefijoCategoria(dto.getPrefijoCategoria());
        return categoriaRepository.save(categoria);
    }

    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO dto){
        Categoria categoriaExistente = categoriaRepository.findById(id).orElse(null);
        if(categoriaExistente != null){
            categoriaExistente.setNombreCategoria(dto.getNombreCategoria());
            categoriaExistente.setDescripcionCategoria(dto.getDescripcionCategoria());
            categoriaExistente.setImagenCategoria(dto.getImagenCategoria());
            categoriaExistente.setPrefijoCategoria(dto.getPrefijoCategoria());
            categoriaRepository.save(categoriaExistente);
            return convertToDTO(categoriaExistente);
        }
        return null;
    }

    public void eliminarCategoria(Long id){
        categoriaRepository.deleteById(id);
    }

    public List<Categoria> obtenerCategorias(){
        return categoriaRepository.findAll();
    }

    public CategoriaDTO convertToDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombreCategoria(categoria.getNombreCategoria());
        dto.setDescripcionCategoria(categoria.getDescripcionCategoria());
        dto.setImagenCategoria(categoria.getImagenCategoria());
        dto.setPrefijoCategoria(categoria.getPrefijoCategoria());
        return dto;
    }
}
