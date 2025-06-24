package com.example.api.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.dto.ActorLoginResponse;
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

        validateEmailUniqueness(request.getEmail());
        Role userRole = getOrDefaultRole(request.getRole());

        Actor actor = createActorFromRequest(request, userRole);
        actorRepository.save(actor);

        System.out.println("Acteur enregistré avec succès : " + actor.getEmail());

        String jwtToken = jwtUtil.generateToken(actor.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(actor.getEmail());
        actor.setRefreshToken(refreshToken);
        actorRepository.save(actor); // Enregistre le token

        return new AuthenticationResponse(jwtToken);
    }
    
    private void validateEmailUniqueness(String email) {
        if (actorRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }
    }

    private Role getOrDefaultRole(String roleName) {
        String effectiveRole = (roleName != null) ? roleName : "ROLE_PASSAGER";
        return roleRepository.findByName(effectiveRole)
            .orElseThrow(() -> new IllegalStateException("Le rôle " + effectiveRole + " est introuvable"));
    }

    private Actor createActorFromRequest(RegisterRequest request, Role role) {
        Actor actor = new Actor();
        actor.setFirstname(request.getFirstname());
        actor.setLastname(request.getLastname());
        actor.setEmail(request.getEmail());
        actor.setPassword(passwordEncoder.encode(request.getPassword()));
        actor.setTelephone(request.getTelephone());
        actor.setCredits(20);
        actor.setActive(true);

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        actor.setRoles(roles);

        actor.setSeatAvailable(request.getSeatAvailable()); // plus de validation ici

        return actor;
    }


    

    public String generateToken(Actor actor) {
        return jwtUtil.generateToken(actor.getEmail());
    }

    public ActorLoginResponse login(String email, String rawPassword) {
    	Actor actor = actorRepository.findByEmailWithRoles(email)
    		    .orElseThrow(() -> new IllegalArgumentException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(rawPassword, actor.getPassword())) {
            throw new IllegalArgumentException("Email ou mot de passe incorrect");
        }

        String token = jwtUtil.generateToken(actor.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(actor.getEmail());

        actor.setRefreshToken(refreshToken); // enregistre le refreshToken en base
        actorRepository.save(actor);

        String roleName = actor.getRoles().stream()
            .findFirst()
            .map(Role::getName)
            .orElse("ROLE_PASSAGER");

        return new ActorLoginResponse(
            actor.getId(),
            actor.getFirstname(),
            actor.getCredits(),
            token,
            refreshToken,
            roleName
        );
    }
}
