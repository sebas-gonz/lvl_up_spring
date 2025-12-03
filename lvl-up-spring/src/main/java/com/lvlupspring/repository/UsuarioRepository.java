package com.lvlupspring.repository;

import com.lvlupspring.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);

    boolean existsByRunUsuario(Integer runUsuario);

    Optional<Usuario> findByEmail(String email);
}
