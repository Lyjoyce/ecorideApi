package com.example.api.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class SeatAvailableReservationDTO {
    private Long reservationId;

    private Long carpoolingId;
    private String fromCity;
    private String toCity;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;
    private int value; // nb de places réservées

    private Long passagerId;
    private String passagerName;
    private String passagerEmail;

    private String conducteurName;
    private String conducteurEmail;

    private String voitureMarque;
    private String voitureModele;
}

