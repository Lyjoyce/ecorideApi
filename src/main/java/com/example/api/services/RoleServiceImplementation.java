package com.example.api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.dto.RoleDTO;
import com.example.api.entities.Role;
import com.example.api.repositories.RoleRepository;

@Service
public class RoleServiceImplementation implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Rôle " + name + " introuvable"));
    }
    
    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(role -> new RoleDTO(role.getId(), role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
    @Override
    public Role save(Role role) {        
        return roleRepository.save(role);
    }
    
    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    }
    	 
    	 
    	 
    	 
    	
    	
       



