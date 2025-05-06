package com.example.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{

}
