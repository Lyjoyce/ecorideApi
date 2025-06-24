
package com.example.api.entities;

import jakarta.persistence.*;

@Entity
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "passager_id", nullable = false)
    private Actor passager;

    @ManyToOne
    @JoinColumn(name = "conducteur_id", nullable = false)
    private Actor conducteur;

    private int note;
   
    @Column(length = 1000)
    private String avis;

    private boolean valide = false;
    
    
    @ManyToOne
    @JoinColumn(name = "carpooling_id", nullable = false)
    private Carpooling carpooling;

    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Actor employe;
    
    @Override
    public String toString() {
        return "Avis{" +
                "id=" + id +
                ", passager=" + passager.getId() +
                ", conducteur=" + conducteur.getId() +
                ", note=" + note +
                ", valide=" + valide +
                '}';
    }


    
    public Long getId() { return id; }
    public Actor getPassager() { return passager; }
    public void setPassager(Actor passager) { this.passager = passager; }

    public Actor getConducteur() { return conducteur; }
    public void setConducteur(Actor conducteur) { this.conducteur = conducteur; }

    public int getNote() { return note; }
    public void setNote(int note) { this.note = note; }

    public String getAvis() { return avis; }
    public void setAvis(String avis) { this.avis = avis; }

    public boolean isValide() { return valide; }
    public void setValide(boolean valide) { this.valide = valide; }

    public Actor getEmploye() { return employe; }
    public void setEmploye(Actor employe) { this.employe = employe; }
	public void setCarpooling(Carpooling carpooling) {
		this.carpooling=carpooling;	
	}
	public Carpooling getCarpooling() {
	    return carpooling;
	}

	
}
