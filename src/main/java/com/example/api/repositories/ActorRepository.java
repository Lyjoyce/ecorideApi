package com.example.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entities.Actor;


public interface ActorRepository extends JpaRepository<Actor,Long> {
	Optional<Actor> findByEmail(String Email);
	Actor findByIsActiveTrue();
}

