package com.example.api.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.dto.RegisterRequest;
import com.example.api.dto.RoleDTO;
import com.example.api.entities.Actor;
import com.example.api.entities.Role;
import com.example.api.repositories.ActorRepository;
import com.example.api.repositories.RoleRepository;


@Service
public class ActorServiceImplementation implements ActorService {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
            .map(role -> new RoleDTO(role.getId(), role.getName()))
            .collect(Collectors.toList());
    }


    @Override
    public Actor register(RegisterRequest request) {
        // Vérification unique que l'email n'existe pas déjà
        if (actorRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà");
        }

        Actor actor = new Actor();
        actor.setFirstname(request.getFirstname());
        actor.setLastname(request.getLastname());
        actor.setEmail(request.getEmail());

        // Encodage du mot de passe
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        actor.setPassword(encodedPassword);

        actor.setCredits(20);

        // Attribution du rôle PASSAGER par défaut
        Role defaultRole = roleRepository.findByName("ROLE_PASSAGER")
            .orElseThrow(() -> new RuntimeException("Rôle 'PASSAGER' non trouvé"));
        actor.setRoles(Set.of(defaultRole));

        return actorRepository.save(actor);
    }

    @Override
    public Actor createActor(Actor actor) {
        if (actorRepository.findByEmail(actor.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Un utilisateur existe déjà avec cet email");
        }
        actor.setCredits(20);
        return actorRepository.save(actor);
    }

    @Override
    public Actor getActorByEmail(String email) {
        return actorRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Aucun utilisateur trouvé avec cet email"));
    }

    @Override
    public Actor getActorById(Long id) {
        return actorRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable avec cet ID"));
    }

    @Override
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @Override
    public Actor registerNewActor(Actor actor, Set<String> roleNames) {
        if (actorRepository.findByEmail(actor.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Un utilisateur existe déjà avec cet email");
        }

        String encodedPassword = passwordEncoder.encode(actor.getPassword());
        actor.setPassword(encodedPassword);

        Set<Role> roles = new HashSet<>();
        for (String name : roleNames) {
            Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role non trouvé : " + name));
            roles.add(role);
        }
        actor.setRoles(roles);
        actor.setCredits(20);
        return actorRepository.save(actor);
    }
}
