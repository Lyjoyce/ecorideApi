package com.example.api.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Voiture {
	@Id @GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String conducteur;
	private String immatriculation;
	private LocalDate date1ereimmatriculation;
	private String marque;
	private String modele;
	private String couleur;
	private String energy;
	
	@ElementCollection
	private List<String> options;
	private boolean ecologique;
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;	
}
