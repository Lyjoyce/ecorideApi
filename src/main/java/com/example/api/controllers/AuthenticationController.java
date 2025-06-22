package com.example.api.controllers;

import com.example.api.dto.ActorLoginResponse;
import com.example.api.dto.AuthenticationResponse;
import com.example.api.dto.LoginRequest;
import com.example.api.dto.RegisterRequest;
import com.example.api.entities.Actor;
import com.example.api.services.ActorService;
import com.example.api.services.AuthenticationService;

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
    
    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
        	System.out.println("Requête register reçue : " + request.getEmail());
        	AuthenticationResponse response = authenticationService.register(request);
        	System.out.println("Enregistrement réussi");
        	return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) { 
        	System.out.println("Erreur (400) : " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
        	e.printStackTrace(); // ← Affiche la trace complète dans la console !
            return ResponseEntity.status(500).body("Erreur serveur");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Actor actor = actorService.getActorByEmail(request.getEmail());

            if (passwordEncoder.matches(request.getPassword(), actor.getPassword())) {
                String jwtToken = authenticationService.generateToken(actor);

                ActorLoginResponse response = new ActorLoginResponse();
                response.setId(actor.getId());
                response.setFirstname(actor.getFirstname());
                response.setLastname(actor.getLastname());
                response.setEmail(actor.getEmail());

                return ResponseEntity.ok(new AuthenticationResponse(jwtToken, response));
            } else {
                return ResponseEntity.status(401).body("Mot de passe incorrect");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Utilisateur non trouvé");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur serveur");
        }
    }
}
