package com.example.api.services;

import java.util.List;

import com.example.api.entities.Role;

public interface RoleService {
	Role findByName(String name);
    List<Role> findAll();
    Role save(Role role);
}

