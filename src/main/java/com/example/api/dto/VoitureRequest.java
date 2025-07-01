package com.example.api.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class VoitureRequest {
    private Long id;

    private Long conducteurId;

    private String immatriculation;
    private LocalDate date1ereimmatriculation;
    private String marque;
    private String modele;
    private String couleur;
    private boolean energy;
    private boolean ecologique;
    private int SeatAvailable;
    
    private String roleName;
    
    
    public Long getConducteurId() { return conducteurId; }
    public String getImmatriculation() { return immatriculation; }
    public LocalDate getDate1ereimmatriculation() { return date1ereimmatriculation; }
    public String getMarque() { return marque; }
    public String getModele() { return modele; }
    public String getCouleur() { return couleur; }
    public boolean getEnergy() { return energy; }
    public boolean isEcologique() { return ecologique; }

	




public void setMarque(String marque) {
    this.marque = marque;
}



public void setModele(String modele) {
    this.modele = modele;
}

public int getSeatAvailable() {
    return SeatAvailable;
}

public void setSeatAvailable(int SeatAvailables) {
    this.SeatAvailable = SeatAvailables;
}


public void setEcologique(boolean ecologique) {
    this.ecologique = ecologique;
}
}
