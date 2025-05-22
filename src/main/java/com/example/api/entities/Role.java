package com.example.api.entities;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name; //  : "ROLE_PASSAGER", "ROLE_CONDUCTEUR", "ROLE_EMPLOYE"
    
    @ManyToMany(mappedBy = "roles")
    private Set<Actor> actors;
    
    @OneToMany(mappedBy = "role")
    private List<Voiture> voitures;


    public Role(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

