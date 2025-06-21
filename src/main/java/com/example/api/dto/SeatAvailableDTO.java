package com.example.api.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.api.entities.SeatAvailable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatAvailableDTO {
    
    private Long id;
    private String fromCity;
    private String toCity;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private int value; // Nombre de places réservées
    private Long passagerId;
    private Long carpoolingId;

    public SeatAvailableDTO(SeatAvailable seat) {
        this.id = seat.getId();
        this.fromCity = seat.getFromCity();
        this.toCity = seat.getToCity();
        this.departureDate = seat.getDepartureDate();
        this.departureTime = seat.getDepartureTime();
        this.arrivalDate = seat.getArrivalDate();
        this.arrivalTime = seat.getArrivalTime();
        this.value = seat.getValue();
        this.passagerId = seat.getPassager() != null ? seat.getPassager().getId() : null;
        this.carpoolingId = seat.getCarpooling() != null ? seat.getCarpooling().getId() : null;
    }
}

