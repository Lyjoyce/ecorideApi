package com.example.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entities.Actor;
import com.example.api.entities.Carpooling;

public interface CarpoolingRepository  extends JpaRepository<Carpooling,Long>{
	List<Carpooling> findByConducteurOrPassagersContains(Actor conducteur, Actor passager);
}
