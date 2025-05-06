package com.example.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.entities.Actor;
import com.example.api.repositories.ActorRepository;

@Service
public class ActorServiceImplementation implements ActorService {
	@Autowired
	private ActorRepository actorRepository;
	
	@Override
	
	public Actor createActor(Actor actor) {
		Optional<Actor> existingActor = actorRepository.findByEmail(actor.getEmail());
		System.out.println("actor dans la bdd:" + existingActor);
		if(existingActor.isPresent()) {
			throw new IllegalArgumentException("Un utilisateur existe déjà avec cet email");	
		}
		return actorRepository.save(actor);
	}

	@Override
	public Actor getActorByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Actor getAllAcrors(Actor findAll) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Actor getActorById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
