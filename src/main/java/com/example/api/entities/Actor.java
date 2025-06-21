package com.example.api.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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

    @Min(1)
    @Max(4)
    @Column(nullable = false)
    private int seatAvailable;

    private int credits = 0;

    @Column(nullable=false)
    private boolean active = true;

    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Voiture> voitures;

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

	

    public void setNote(int round) {}

    public int getNote() {
        return 0;
    }

    public LocalDate getBirthday() {
        return null;
    }
    
    public String getPrimaryRoleName() {
        return roles.stream()
                .findFirst()
                .map(Role::getName)
                .orElse("UNKNOWN");
    }

}
 