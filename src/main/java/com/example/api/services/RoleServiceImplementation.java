package com.example.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.entities.Role;
import com.example.api.repositories.RoleRepository;

@Service
public class RoleServiceImplementation implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            throw new IllegalArgumentException("Rôle introuvable : " + name);
        }
        return role;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }
}



