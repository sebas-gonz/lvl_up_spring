package com.lvlupspring.service;

import com.lvlupspring.entity.Rol;
import com.lvlupspring.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolService {
    private  final RolRepository rolRepository;

    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    public Rol findByNombreRol(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol).orElse(null);
    }

    public void deleteById(Long id) {
        rolRepository.deleteById(id);
    }

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    public  Rol findById(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

}
