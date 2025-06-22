package com.example.api.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.dto.AuthenticationResponse;
import com.example.api.dto.RegisterRequest;
import com.example.api.entities.Actor;
import com.example.api.entities.Role;
import com.example.api.repositories.ActorRepository;
import com.example.api.repositories.RoleRepository;
import com.example.api.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor


public class AuthenticationService {
	
    private final ActorRepository actorRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthenticationResponse register(RegisterRequest request) {
    	System.out.println("Tentative d'enregistrement pour : " + request.getEmail());

        if (actorRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        // Utilise le rôle fourni par le frontend, ou ROLE_PASSAGER par défaut
        String roleName = request.getRole() != null ? request.getRole() : "ROLE_PASSAGER";

        Role userRole = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalStateException("Le rôle " + roleName + " est introuvable"));

        Actor actor = new Actor();
        actor.setFirstname(request.getFirstname());
        actor.setLastname(request.getLastname());
        actor.setEmail(request.getEmail());
        actor.setPassword(passwordEncoder.encode(request.getPassword()));
        actor.setCredits(20); 
        actor.setActive(true);
        
        actor.setSeatAvailable(request.getSeatAvailable());
        actor.setTelephone(request.getTelephone());
        
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        actor.setRoles(roles);
        
        System.out.println("Sauvegarde de l'acteur : " + actor.getEmail());
        actorRepository.save(actor);     
        String jwtToken = jwtUtil.generateToken(actor.getEmail());
        
        System.out.println("Token généré avec succès");
        return new AuthenticationResponse(jwtToken);         
    }
    
    public String generateToken(Actor actor) {
        return jwtUtil.generateToken(actor.getEmail());
    }	
}
