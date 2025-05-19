package com.example.api.config;  // ou com.example.api.init

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.api.entities.Role;
import com.example.api.repositories.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        List<String> roleNames = List.of("ROLE_PASSAGER", "ROLE_CONDUCTEUR", "ROLE_EMPLOYE");

        for (String name : roleNames) {
            if (!roleRepository.existsByName(name)) {
                roleRepository.save(new Role(name));
            }
        }
    }
}
