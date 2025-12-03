package com.lvlupspring.controller;

import com.lvlupspring.entity.Region;
import com.lvlupspring.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/regiones")
@RequiredArgsConstructor
public class RegionController {
    private  final RegionService regionService;

    @GetMapping
    public ResponseEntity<List<Region>> findAll() {
        return ResponseEntity.ok(regionService.findAll());
    }
}
