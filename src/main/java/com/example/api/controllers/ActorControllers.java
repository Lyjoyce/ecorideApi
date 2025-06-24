package com.example.api.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.entities.Actor;
import com.example.api.entities.Role;
import com.example.api.entities.Voiture;
import com.example.api.repositories.ActorRepository;
import com.example.api.repositories.RoleRepository;
import com.example.api.repositories.VoitureRepository;

@RestController
@RequestMapping("/api/v1/actors")

public class ActorControllers {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Test simple
    @GetMapping("/test")
    public String testCallActor() {
        return "ActorController ok";
    }

    // Récupérer tous les acteurs
    @GetMapping
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    // Récupérer tous les actors actifs
    @GetMapping("/active")
    public List<Actor> getAllActiveActors() {
        return actorRepository.findByActiveTrue();
    }

    // Récupérer un actor par id
    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable Long id) {
        return actorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // @GetMapping("/{id}/carpoolings") → pour afficher les trajets proposés par cet acteur.
    // @GetMapping("/{id}/reservations") → pour ses trajets réservés.

    // Mettre à jour un actor (exemple sur lastname, à étendre selon besoin)
    @PutMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Actor actorDetails) {
        return actorRepository.findById(id)
                .map(actor -> {
                    actor.setLastname(actorDetails.getLastname());
                    // TODO: mettre à jour d'autres champs si nécessaire
                    return ResponseEntity.ok(actorRepository.save(actor));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Devenir conducteur avec validation voiture
    @PutMapping("/{id}/become-driver")
    public ResponseEntity<?> becomeDriver(@PathVariable Long id, @RequestParam Long voitureId) {
        Optional<Actor> optionalActor = actorRepository.findById(id);
        Optional<Voiture> optionalVoiture = voitureRepository.findById(voitureId);

        if (optionalActor.isEmpty() || optionalVoiture.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Acteur ou voiture non trouvé(e).");
        }

        Actor actor = optionalActor.get();
        Voiture voiture = optionalVoiture.get();

        // Vérifie que la voiture appartient bien à cet acteur
        if (voiture.getConducteurActor() == null || !Objects.equals(voiture.getConducteurActor().getId(), id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cette voiture ne vous appartient pas.");
        }

        Optional<Role> conducteurRole = roleRepository.findByName("ROLE_CONDUCTEUR");
        if (conducteurRole.isPresent()) {
            actor.getRoles().add(conducteurRole.get());
            actorRepository.save(actor);
            return ResponseEntity.ok("L'acteur est maintenant conducteur.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Rôle conducteur non trouvé.");
        }
    }

    // Soft delete (désactivation) d'un acteur
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Actor> softDeleteActor(@PathVariable Long id) {
        return actorRepository.findById(id)
                .map(actor -> {
                    actor.setActive(false);
                    return ResponseEntity.ok(actorRepository.save(actor));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
