package com.example.api.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
@Entity
public class Carpooling {
	@Id @GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private String fromCity;
	private String toCity;
	private String departureAdress;
	private String arrivalAdress;
	private LocalDate departureDate;
	private LocalTime departureTime;
	private LocalDate arrivalDate;
	private LocalTime arrivalTime ;
	
	private String option;
	private boolean energy;
	private String conducteur;
	private int seatAvailable;
	
	private int prix;
	private int note;
	private int duree;
	
	 // Un trajet a plusieurs avis
    @OneToMany(mappedBy = "carpooling", cascade = CascadeType.ALL)
    private List<Avis> avisList = new ArrayList<>();

    // Un trajet a plusieurs places disponibles
    @OneToMany(mappedBy = "carpooling", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatAvailable> reservations = new ArrayList<>();

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
