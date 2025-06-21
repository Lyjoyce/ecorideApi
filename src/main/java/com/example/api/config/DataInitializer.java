package com.example.api.config;

import com.example.api.entities.Role;
import com.example.api.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        List<String> defaultRoles = Arrays.asList(
            "ROLE_ACTOR",
            "ROLE_PASSAGER",
            "ROLE_CONDUCTEUR",
            "ROLE_EMPLOYE",
            "ROLE_ADMIN"
        );

        for (String roleName : defaultRoles) {
            if (!roleRepository.existsByName(roleName)) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
                System.out.println("Inserted role: " + roleName);
            }
        }
    }
}
