package com.example.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entities.Carpooling;

public interface CarpoolingRepository  extends JpaRepository<Carpooling,Long>{

}
