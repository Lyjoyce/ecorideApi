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
import com.example.api.repositories.ActorRepository;
import com.example.api.repositories.AvisRepository;

@RestController
@RequestMapping("/api/v1/avis")
@CrossOrigin(origins = "*")
public class AvisControllers {

    @Autowired
    private AvisRepository avisRepository;

    @Autowired
    private ActorRepository actorRepository;

    // 🔸 Passager laisse un avis sur un conducteur (non validé)
    @PostMapping("/passager/{passagerId}/conducteur/{conducteurId}")
    public ResponseEntity<Avis> laisserAvis(
            @PathVariable Long passagerId,
            @PathVariable Long conducteurId,
            @RequestBody AvisRequest avisRequest) {

        Actor passager = actorRepository.findById(passagerId).orElse(null);
        Actor conducteur = actorRepository.findById(conducteurId).orElse(null);

        if (passager == null || conducteur == null) {
            return ResponseEntity.notFound().build();
        }

        Avis avis = new Avis();
        avis.setNote(avisRequest.getNote());
        avis.setAvis(avisRequest.getAvis());
        avis.setPassager(passager);
        avis.setConducteur(conducteur);
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

        // Mise à jour de la note du conducteur
        Actor conducteur = avis.getConducteur();
        if (conducteur != null) {
            List<Avis> avisValides = avisRepository.findByConducteurAndValideTrue(conducteur);
            double moyenne = avisValides.stream()
                    .mapToInt(Avis::getNote)
                    .average()
                    .orElse(0.0);

            conducteur.setNote((int) Math.round(moyenne));
            actorRepository.save(conducteur);
        }

        return ResponseEntity.ok(avis);
    }

    // 🔸 Liste des avis non validés
    @GetMapping("/non-valides")
    public List<Avis> getAvisNonValidés() {
        return avisRepository.findByValideFalse();
    }

    // DTO pour réception d'avis
    public static class AvisRequest {
        private int note;
        private String avis;

        public int getNote() { return note; }
        public void setNote(int note) { this.note = note; }

        public String getAvis() { return avis; }
        public void setAvis(String avis) { this.avis = avis; }
    }
}
