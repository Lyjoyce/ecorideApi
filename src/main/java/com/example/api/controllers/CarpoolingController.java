package com.example.api.controllers;

import com.example.api.entities.Carpooling;
import com.example.api.repositories.CarpoolingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carpooling")
public class CarpoolingController {

    @Autowired
    private CarpoolingRepository carpoolingRepository;

    @PostMapping("/create")
    public ResponseEntity<Carpooling> createCarpooling(@RequestBody Carpooling carpooling) {
        Carpooling saved = carpoolingRepository.save(carpooling);
        return ResponseEntity.ok(saved);
    }
}
