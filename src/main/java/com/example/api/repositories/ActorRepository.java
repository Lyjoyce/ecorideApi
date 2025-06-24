package com.example.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.api.entities.Actor;
import com.example.api.entities.Voiture;

public interface ActorRepository extends JpaRepository<Actor,Long> {
	Optional<Actor> findByEmail(String email);

    @Query("SELECT a FROM Actor a LEFT JOIN FETCH a.roles WHERE a.email = :email")
    Optional<Actor> findByEmailWithRoles(@Param("email") String email);
    
	List<Actor> findByActiveTrue();

}






