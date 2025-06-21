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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ActorRepository actorRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse register(RegisterRequest request) {

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

        // Ajoute le rôle dans le Set<Role>
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        actor.setRoles(roles);

        actorRepository.save(actor);

        String jwtToken = jwtService.generateToken(actor);
        return new AuthenticationResponse(jwtToken);
    }

}
