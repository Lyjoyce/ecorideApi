package com.example.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entities.Voiture;

public interface VoitureRepository extends JpaRepository<Voiture,Long> {

}
