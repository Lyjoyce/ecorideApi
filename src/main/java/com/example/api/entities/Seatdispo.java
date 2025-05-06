package com.example.api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Seatdispo {
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
}
