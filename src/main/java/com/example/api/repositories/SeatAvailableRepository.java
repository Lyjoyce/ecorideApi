package com.example.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entities.SeatAvailable;

public interface SeatAvailableRepository  extends JpaRepository<SeatAvailable,Long> {

}

