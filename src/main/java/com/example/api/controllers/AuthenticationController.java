package com.example.api.controllers;

import com.example.api.dto.LoginRequest;
import com.example.api.dto.RegisterRequest;
import com.example.api.entities.Actor;
import com.example.api.services.ActorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private ActorService actorService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            Actor savedActor = actorService.register(request);
            // Optionnel : Ne renvoyer que des données publiques (DTO), pas le password !
            savedActor.setPassword(null);
            return ResponseEntity.ok(savedActor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur serveur");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Actor actor = actorService.getActorByEmail(request.getEmail());

            if (passwordEncoder.matches(request.getPassword(), actor.getPassword())) {
                // TODO: Générer un token JWT ici et le retourner dans la réponse
                return ResponseEntity.ok("Login successful!");
            } else {
                return ResponseEntity.status(401).body("Mot de passe incorrect");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Utilisateur non trouvé");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur serveur");
        }
    }
}
