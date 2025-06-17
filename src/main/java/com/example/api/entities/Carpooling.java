package com.example.api.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
@Entity
public class Carpooling {
	@Id @GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String itinerary;
	private String carpooling;
	private String conducteur;
	private String immatriculation;
	private LocalDate date1ereimmatriculation;
	private String marque;
	private String modele;
	private String couleur;
	private boolean energy;
	private boolean ecologique;
	private String preferences;
	private boolean animal;
	private boolean nosmoke;
	private boolean seatone;
	private boolean seatwo;
	private boolean seatthree;
	private String lieudep;
	private String lieuarr;
	private String jourdep;
	private String jourarr;
	private int heuredep;
	private int heurearr ;
	private int duree;
	private int prix;
	private int nbPlaceDisponible;
	@OneToMany(mappedBy="carpooling")
	private List<Seatdispo> seatdispo;	
}
