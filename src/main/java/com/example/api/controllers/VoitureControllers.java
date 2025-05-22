package com.example.api.controllers;

import com.example.api.dto.VoitureRequest;
import com.example.api.entities.Role;
import com.example.api.entities.Voiture;
import com.example.api.repositories.RoleRepository;
import com.example.api.repositories.VoitureRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voitures")
public class VoitureControllers {

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping
    public ResponseEntity<?> createVoiture(@RequestBody VoitureRequest request) {
    	Role role = roleRepository.findByName(request.getRoleName());
        if (role == null) {
            return ResponseEntity.badRequest().body("Role not found: " + request.getRoleName());
        }
    	
        Voiture voiture = new Voiture();
        voiture.setConducteur(request.getConducteur());
        voiture.setImmatriculation(request.getImmatriculation());
        voiture.setDate1ereimmatriculation(request.getDate1ereimmatriculation());
        voiture.setMarque(request.getMarque());
        voiture.setModele(request.getModele());
        voiture.setCouleur(request.getCouleur());
        voiture.setEnergy(request.getEnergy());
        voiture.setOptions(request.getOptions());
        voiture.setEcologique(request.isEcologique());
        voiture.setRole(role);

        Voiture saved = voitureRepository.save(voiture);
        return ResponseEntity.ok(saved);
    }
}

