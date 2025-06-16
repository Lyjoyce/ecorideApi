package com.example.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 🔸 Passager laisse un avis (non validé)
    @PostMapping("/actor/{id}")
    public ResponseEntity<Avis> laisserAvis(@PathVariable Long id, @RequestBody AvisRequest avisRequest) {
        return actorRepository.findById(id).map(actor -> {
            Avis avis = new Avis();
            avis.setNote(avisRequest.getNote());
            avis.setAvis(avisRequest.getAvis());
            avis.setActor(actor);
            avis.setValide(false); // en attente de validation
            Avis savedAvis = avisRepository.save(avis);
            return ResponseEntity.ok(savedAvis);
        }).orElse(ResponseEntity.notFound().build());
    }

    // 🔸 Employé valide un avis (et met à jour la moyenne du conducteur)
    @PutMapping("/{avisId}/valider")
    public ResponseEntity<Avis> validerAvis(@PathVariable Long avisId) {
        return avisRepository.findById(avisId).map(avis -> {
            avis.setValide(true);
            avisRepository.save(avis);

            Actor actor = avis.getActor();
            if (actor != null) {
                List<Avis> avisValides = avisRepository.findByActorAndValideTrue(actor);
                if (avisValides != null && !avisValides.isEmpty()) {
                    double moyenne = avisValides.stream()
                        .filter(a -> a.getNote() >= 0)
                        .mapToInt(Avis::getNote)
                        .average()
                        .orElse(0.0);

                    actor.setNote((int) Math.round(moyenne));
                    actorRepository.save(actor);
                }
            }

            return ResponseEntity.ok(avis);
        }).orElse(ResponseEntity.notFound().build());
    }

    // 🔸 Liste des avis non validés
    @GetMapping("/non-valides")
    public List<Avis> getAvisNonValidés() {
        return avisRepository.findByValideFalse();
    }

    // DTO interne pour réceptionner les données d’un avis
    public static class AvisRequest {
        private int note;
        private String avis;

        public int getNote() { return note; }
        public void setNote(int note) { this.note = note; }

        public String getAvis() { return avis; }
        public void setAvis(String avis) { this.avis = avis; }
    }
}
