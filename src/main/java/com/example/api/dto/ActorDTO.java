package com.example.api.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.api.entities.Actor;
import com.example.api.entities.Avis;

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
    private Set<String> roleNames;
    private String telephone;
    private LocalDate birthday;
    private int note;
    private List<Avis> avis;
    private String immatriculation;
    private LocalDate date1ereimmatriculation;
    private String marque;
    private String preferences;
    private boolean ecologique;
    private boolean animal;
    private boolean nosmoke;
    private int credits;
    private boolean isActive;
    private List<SeatAvailableDTO> seatReservations;
	private Object seatAvailable;

	 

    public ActorDTO(Actor actor) {
        this.id = actor.getId();
        this.firstname = actor.getFirstname();
        this.lastname = actor.getLastname();
        this.email = actor.getEmail();
        this.telephone = actor.getTelephone();
        this.birthday = actor.getBirthday();
        this.note = actor.getNote();
        this.avis = actor.getAvisEcrits();
        this.immatriculation = actor.getImmatriculation();
        this.date1ereimmatriculation = actor.getDate1ereimmatriculation();
        this.marque = actor.getMarque();
        this.preferences = actor.getPreferences();
        this.ecologique = actor.isEcologique();
        this.animal = actor.isAnimal();
        this.nosmoke = actor.isNosmoke();
        this.credits = actor.getCredits();
        this.isActive = actor.isActive();  
        this.seatAvailable = actor.getSeatReservations().stream()
                .map(SeatAvailableDTO::new)
                .collect(Collectors.toList());
        }



	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(Set<String> roleNames) {
        this.roleNames = roleNames;
    }



	public void setPassword(String encodedPassword) {
		// TODO Auto-generated method stub
		
	}
    }

