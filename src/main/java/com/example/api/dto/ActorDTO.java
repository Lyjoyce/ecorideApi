package com.example.api.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.example.api.entities.Actor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private int telephone;
    private LocalDate birthday;
    private int note;
    private String avis;
    private String immatriculation;
    private LocalDate date1ereimmatriculation;
    private String marque;
    private String preferences;
    private boolean ecologique;
    private boolean animal;
    private boolean nosmoke;
    private boolean seatone;
    private boolean seattwo;
    private boolean seatthree;
    private int nbplace;
    private int credits;
    private boolean isActive;
    private List<SeatdispoDTO> seatdispo; 

    public ActorDTO(Actor actor) {
        this.id = actor.getId();
        this.firstname = actor.getFirstname();
        this.lastname = actor.getLastname();
        this.email = actor.getEmail();
        this.telephone = actor.getTelephone();
        this.birthday = actor.getBirthday();
        this.note = actor.getNote();
        this.avis = actor.getAvis();
        this.immatriculation = actor.getImmatriculation();
        this.date1ereimmatriculation = actor.getDate1ereimmatriculation();
        this.marque = actor.getMarque();
        this.preferences = actor.getPreferences();
        this.ecologique = actor.isEcologique();
        this.animal = actor.isAnimal();
        this.nosmoke = actor.isNosmoke();
        this.seatone = actor.isSeatone();
        this.seattwo = actor.isSeattwo();
        this.seatthree = actor.isSeatthree();
        this.nbplace = actor.getNbPlaceDisponible();
        this.credits = actor.getCredits();
        this.isActive = actor.isActive();  
        this.seatdispo = actor.getSeatdispo().stream()
                .map(SeatdispoDTO::new)
                .collect(Collectors.toList());
        }
    }

