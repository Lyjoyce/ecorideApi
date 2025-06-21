package com.example.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{
	boolean existsByName(String name);
	Optional<Role> findByName(String name);
}

