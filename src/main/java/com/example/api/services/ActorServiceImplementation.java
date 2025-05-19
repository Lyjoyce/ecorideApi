package com.example.api.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Actor createActor(Actor actor) {
        Optional<Actor> existingActor = actorRepository.findByEmail(actor.getEmail());
        if (existingActor.isPresent()) {
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
        Set<Role> roles = new HashSet<>();
        for (String name : roleNames) {
            Role role = roleRepository.findByName(name);
            if (role != null) {
                roles.add(role);
            } else {
                throw new IllegalArgumentException("Rôle introuvable : " + name);
            }
        }
        actor.setRoles(roles);
        actor.setCredits(20);
        return actorRepository.save(actor);
    }
}
