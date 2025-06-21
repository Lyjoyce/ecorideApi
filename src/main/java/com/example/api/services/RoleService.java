package com.example.api.services;

import java.util.List;

import com.example.api.dto.RoleDTO;
import com.example.api.entities.Role;

public interface RoleService {
	Role findByName(String name);
    List<Role> findAll();
    Role save(Role role);
	void delete(Role role);
	List<RoleDTO> getAllRoles();

}

