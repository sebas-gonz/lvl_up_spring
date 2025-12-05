package com.lvlupspring.repository;

import com.lvlupspring.entity.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta,Long> {

    List<Boleta> findByUsuario_UsuarioId(Long usuarioId);
}
