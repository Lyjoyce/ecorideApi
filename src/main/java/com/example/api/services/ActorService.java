package com.example.api.services;

import java.util.List;
import java.util.Set;

import com.example.api.entities.Actor;

public interface ActorService {
	Actor createActor(Actor actor);
	Actor getActorByEmail(String email);
	Actor getActorById(Long id);
	List<Actor> getAllActors();
	Actor registerNewActor(Actor actor, Set<String> roleNames);

}

