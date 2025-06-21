package com.example.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entities.Actor;
import com.example.api.entities.Avis;

public interface AvisRepository extends JpaRepository<Avis, Long> {
    List<Avis> findByValideFalse(); // pour que l'employé valide
    List<Avis> findByPassagerAndValideTrue(Actor passager);
    List<Avis> findByConducteurAndValideTrue(Actor conducteur);

}

