package com.example.api.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actor {

    @Id 
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String telephone;

    private LocalDate birthday; 
    private int note = 0;
    
    @Lob
    @Column(name = "photo")
    private byte[] photo;

    private String immatriculation;
    private LocalDate date1ereimmatriculation;
    private String marque;
    private String preferences;

    private boolean ecologique;
    private boolean animal;
    private boolean nosmoke;
    
    public List<Avis> getAvisRecusValidés() {
        return avisRecus.stream()
            .filter(Avis::isValide)
            .collect(Collectors.toList());
    }

    
   
    @Min(1)
    @Max(4)
    @Column(nullable = false)
    private Integer seatAvailable = 1; // valeur par défaut
    private int credits = 0;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Voiture> voitures = new ArrayList<>();

    @OneToMany(mappedBy = "passager", cascade = CascadeType.ALL)
    private List<Avis> avisEcrits = new ArrayList<>();

    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL)
    private List<Avis> avisRecus = new ArrayList<>();

    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avis> avisValidés = new ArrayList<>();

    @OneToMany(mappedBy = "passager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatAvailable> seatReservations = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "actor_roles",
        joinColumns = @JoinColumn(name = "actor_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public String getPrimaryRoleName() {
        return roles.stream()
                .findFirst()
                .map(Role::getName)
                .orElse("UNKNOWN");
    }
    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Carpooling> trajetsProposes = new ArrayList<>();
    
    @Column(length = 1000)
    private String refreshToken;
}
