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
	private String immatriculation;
	private LocalDate date1ereimmatriculation;
	private String marque;
	private String modele;
	private String couleur;
	private String energy;
	private int nbPlacesDisponibles;

	
	@ElementCollection
	private List<String> options;
	private boolean ecologique;
	
	 @ManyToOne
	    @JoinColumn(name = "conducteur_id") // L'actor propriétaire de cette voiture
	    private Actor conducteur;
	

	public void setConducteurActor(Actor conducteur2) {
		// TODO Auto-generated method stub
		
	}

	public Actor getConducteurActor() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setMarque(String marque) { this.marque = marque; }
	public void setModele(String modele) { this.modele = modele; }
	public void setEcologique(boolean ecologique) { this.ecologique = ecologique; }
	
	public void setImmatriculation(String immatriculation) { this.immatriculation = immatriculation; }
	public void setDate1ereimmatriculation(LocalDate date) { this.date1ereimmatriculation = date; }
	public void setCouleur(String couleur) { this.couleur = couleur; }
	public void setEnergy(String energy) { this.energy = energy; }
	public void setOptions(List<String> options) { this.options = options; }

	public int getNbPlacesDisponible() {
		return nbPlacesDisponibles;
	}

}
