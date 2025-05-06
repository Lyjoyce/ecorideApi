package com.example.api.services;

import com.example.api.entities.Actor;

public interface ActorService {
	Actor createActor(Actor actor);
	Actor getActorByEmail(String email);
	Actor getAllAcrors(Actor findAll);
	Actor getActorById(Long id);

}
