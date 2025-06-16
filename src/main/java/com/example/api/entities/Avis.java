package com.example.api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Avis {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int note;
    private String avis;
    private boolean valide = false; // par défaut, non validé

    @ManyToOne
    @JoinColumn(name = "actor_id")
    private Actor actor;

	public void setNote(int note2) {
		// TODO Auto-generated method stub
		
	}

	public void setAvis(String avis2) {
		// TODO Auto-generated method stub
		
	}

	public void setActor(Actor actor2) {
		// TODO Auto-generated method stub
		
	}

	public void setValide(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public Actor getActor() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getNote() {
		// TODO Auto-generated method stub
		return 0;
	}
    

}
