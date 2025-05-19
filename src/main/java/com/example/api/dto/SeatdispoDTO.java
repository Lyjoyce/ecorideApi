package com.example.api.dto;

import java.time.LocalDate;

import com.example.api.entities.Seatdispo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatdispoDTO {
    private Long id;
    private LocalDate date;
    private String heureDepart;
    private String heureArrivee;
    private String villeDepart;
    private String villeArrivee;
    private int nbPlaceDisponible;

    public SeatdispoDTO(Seatdispo seatdispo) {
        this.id = seatdispo.getId();
        this.date = seatdispo.getDate();
        this.heureDepart = seatdispo.getHeureDepart();
        this.villeDepart = seatdispo.getVilleDepart();
        this.villeArrivee = seatdispo.getVilleArrivee();
        this.nbPlaceDisponible = seatdispo.getNbPlaceDisponible();
    }
}

