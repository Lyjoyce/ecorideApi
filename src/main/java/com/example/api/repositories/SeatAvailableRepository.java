package com.example.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entities.Actor;
import com.example.api.entities.Carpooling;
import com.example.api.entities.SeatAvailable;

public interface SeatAvailableRepository  extends JpaRepository<SeatAvailable,Long> {
	List<SeatAvailable> findByPassagerAndCarpoolingAndConfirmedTrue(Actor passager, Carpooling carpooling);

}

