package com.lvlupspring.repository;

import com.lvlupspring.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria_CategoriaId(Long categoriaId);

    List<Producto> findByOfertaTrue();

}
