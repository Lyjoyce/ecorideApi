package com.example.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.VoitureRequest;
import com.example.api.entities.Actor;
import com.example.api.entities.Voiture;
import com.example.api.repositories.ActorRepository;
import com.example.api.repositories.RoleRepository;
import com.example.api.repositories.VoitureRepository;

@RestController
@RequestMapping("/api/voitures")
public class VoitureControllers {
	
	@Autowired private ActorRepository actorRepository;

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private RoleRepository roleRepository;

        
    @PostMapping
    public ResponseEntity<?> createVoiture(@RequestBody VoitureRequest request) {
            Actor conducteur = actorRepository.findById(request.getConducteurId()).orElse(null);
            if (conducteur == null) return ResponseEntity.badRequest().body("Conducteur introuvable");
    	
        Voiture voiture = new Voiture();
        voiture.setConducteurActor(conducteur);
        voiture.setImmatriculation(request.getImmatriculation());
        voiture.setDate1ereimmatriculation(request.getDate1ereimmatriculation());
        voiture.setMarque(request.getMarque());
        voiture.setModele(request.getModele());
        voiture.setCouleur(request.getCouleur());
        voiture.setEnergy(request.getEnergy());
        voiture.setEcologique(request.isEcologique());

        Voiture saved = voitureRepository.save(voiture);
        return ResponseEntity.ok(saved);
    }
}

