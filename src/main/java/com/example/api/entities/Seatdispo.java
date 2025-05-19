package com.example.api.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Seatdispo {
	
		private String fromCity;
	    private String toCity;
	    private LocalDate departureDate;
	    private LocalDate departureTime;
	    private String arrivalDate;
	    private String arrivalTime;
	    
	    private int nbPlaceDisponible;

	
	
	@Id @GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	@Column(name="Disponibility", nullable=false)
	private int value;
	
	@ManyToOne
	@JoinColumn(name="actor_id")
	private Actor actor;
	@ManyToOne
	@JoinColumn(name="carpooling_id")
	private Carpooling carpooling;
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
	public LocalDate getDate() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getHeureDepart() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getVilleDepart() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getVilleArrivee() {
		// TODO Auto-generated method stub
		return null;
	}
	public int getNbPlaceDisponible() {
		// TODO Auto-generated method stub
		return 0;
	}
}
