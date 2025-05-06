package com.example.api.entities;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Voiture {
	@Id @GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String conducteur;
	private String immatriculation;
	private int date1ereimmatriculation;
	private String marque;
	private String modele;
	private String couleur;
	private String energy;
	private List<String> options;
	private String ecologique;
	@ManyToOne
	@JoinColumn(name="carpooling_id")
	private Carpooling carpooling;	
}
