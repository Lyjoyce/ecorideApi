package com.example.api.entities;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actor {
	@Id @GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private int telephone;
	private String birthday;
	private int note;
	private String avis;
	private String immatriculation;
	private String date1ereimmatriculation;
	private String marque;
	private String ecologique;
	private String preferences;
	private String animal;
	private String nosmoke;
	private String seatone;
	private String seattwo;
	private String seatthree;
	private int nbplace;
	private int credit;
	@Column(nullable=false)
	private boolean isActive=true;
	@OneToMany (mappedBy= "actor")
	private List<Seatdispo> seatdispo;
}
