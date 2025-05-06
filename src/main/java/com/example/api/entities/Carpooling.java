package com.example.api.entities;

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
	private String pays;
	private String region;
	private String itinerary;
	private String carpooling;
	private String conducteur;
	private String immatriculation;
	private int date1ereimmatriculation;
	private String marque;
	private String modele;
	private String couleur;
	private String energy;
	private String ecologique;
	private String preferences;
	private String animal;
	private String nosmoke;
	private String avis;
	private String seatone;
	private String seatwo;
	private String seatthree;
	private String lieudep;
	private String lieuarr;
	private String jourdep;
	private String jourarr;
	private int heuredep;
	private int heurearr ;
	private int duree;
	private int prix;
	private int note;
	private int nbplace;
	@OneToMany(mappedBy="carpooling")
	private List<Seatdispo> seatdispo;
	@OneToMany(mappedBy="carpooling")
	private List<Voiture> voitures;
	
}
