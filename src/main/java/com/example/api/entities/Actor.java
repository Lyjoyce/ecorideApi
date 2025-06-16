package com.example.api.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
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
	private String telephone;
	@Lob
	@Column(name = "photo")
	private byte[] photo;
	
	private String immatriculation;
	private LocalDate date1ereimmatriculation;
	private String marque;
	private String preferences;
	
	private boolean ecologique;
	private boolean animal;
	private boolean nosmoke;
	private boolean seatone;
	private boolean seattwo;
	private boolean seatthree;
	
	private int nbPlaceDisponible;
	private int credits = 0;
	@Column(nullable=false)
	
	private boolean active=true;
	
	@OneToMany(mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Avis> avis;


	@OneToMany (mappedBy= "actor")
	private List<Seatdispo> seatdispo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	    name = "actor_role",
	    joinColumns = @JoinColumn(name = "actor_id"),
	    inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles = new HashSet<>();
	


	public void setNote(int round) {
		// TODO Auto-generated method stub
		
	}

	public int getNote() {
		// TODO Auto-generated method stub
		return 0;
	}

	public LocalDate getBirthday() {
		// TODO Auto-generated method stub
		return null;
	}

}




