package com.lvlupspring.service;

import com.lvlupspring.entity.Region;
import com.lvlupspring.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    public Region findById(Long id) {
        return regionRepository.findById(id).orElseThrow(() -> new RuntimeException("Región no encontrada"));
    }
}
