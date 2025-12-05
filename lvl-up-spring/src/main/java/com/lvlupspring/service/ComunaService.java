package com.lvlupspring.service;

import com.lvlupspring.entity.Comuna;
import com.lvlupspring.repository.ComunaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComunaService {
    private final ComunaRepository comunaRepository;

    public Comuna findById(Long id) {
        return comunaRepository.findById(id).orElse(null);
    }

    public List<Comuna> findAll() {
        return comunaRepository.findAll();
    }

    public List<Comuna> findByRegionId(Long regionId) {
        return comunaRepository.findByRegion_RegionId(regionId);
    }

    public Comuna findByNombreComuna(String nombreComuna) {
        return comunaRepository.findByNombreComuna(nombreComuna).orElse(null);
    }

}
