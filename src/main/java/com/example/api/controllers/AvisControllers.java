package com.example.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.entities.Actor;
import com.example.api.entities.Avis;
import com.example.api.entities.Carpooling;
import com.example.api.repositories.ActorRepository;
import com.example.api.repositories.AvisRepository;
import com.example.api.repositories.CarpoolingRepository;
import com.example.api.repositories.SeatAvailableRepository;

@RestController
@RequestMapping("/api/v1/avis")

public class AvisControllers {

    @Autowired
    private AvisRepository avisRepository;

    @Autowired
    private ActorRepository actorRepository;
    
    @Autowired
    private CarpoolingRepository carpoolingRepository;

    @Autowired
    private SeatAvailableRepository seatAvailableRepository;

    // 🔸 Passager laisse un avis sur un conducteur (non validé)
    @PostMapping("/passager/{passagerId}/carpooling/{carpoolingId}/conducteur/{conducteurId}")
    public ResponseEntity<?> laisserAvis(
            @PathVariable Long passagerId,
            @PathVariable Long carpoolingId,
            @PathVariable Long conducteurId,
            @RequestBody AvisRequest avisRequest) {

        Actor passager = actorRepository.findById(passagerId).orElse(null);
        Actor conducteur = actorRepository.findById(conducteurId).orElse(null);
        Carpooling carpooling = carpoolingRepository.findById(carpoolingId).orElse(null);

        if (passager == null || conducteur == null || carpooling == null) {
            return ResponseEntity.badRequest().body("Passager, conducteur ou trajet introuvable.");
        }

        // 🔍 Vérifie que le passager a une réservation confirmée sur ce trajet
        boolean aReserve = carpooling.getReservations().stream()
            .anyMatch(resa -> resa.getPassager().getId().equals(passagerId) && resa.isConfirmed());

        if (!aReserve) {
            return ResponseEntity.status(403).body("Seuls les passagers ayant réservé peuvent laisser un avis.");
        }

        // Création de l'avis
        Avis avis = new Avis();
        avis.setAvis(avisRequest.getAvis());
        avis.setPassager(passager);
        avis.setConducteur(conducteur);
        avis.setCarpooling(carpooling);
        avis.setValide(false);

        Avis savedAvis = avisRepository.save(avis);
        return ResponseEntity.ok(savedAvis);
    }
   
    // 🔸 Employé valide un avis
    @PutMapping("/{avisId}/valider")
    public ResponseEntity<Avis> validerAvis(
            @PathVariable Long avisId,
            @RequestParam Long employeId) {

        Avis avis = avisRepository.findById(avisId).orElse(null);
        Actor employe = actorRepository.findById(employeId).orElse(null);

        if (avis == null || employe == null) {
            return ResponseEntity.notFound().build();
        }

        avis.setValide(true);
        avis.setEmploye(employe);
        avisRepository.save(avis);

        return ResponseEntity.ok(avis);
    }

    // 🔸 Liste des avis non validés
    @GetMapping("/non-valides")
    public List<Avis> getAvisNonValidés() {
        return avisRepository.findByValideFalse();
    }

    // DTO pour réception d'avis
    public static class AvisRequest {
    	
        private String avis;

        public String getAvis() { return avis; }
        public void setAvis(String avis) { this.avis = avis; }
    }
}
