package com.example.api.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;

@Data
public class CarpoolingResponseDTO {
    private String fromCity;
    private String toCity;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate arrivalDate;
    private LocalTime arrivalTime;

    private String preferences;
    private String energy;

    private String conducteur;     // nom complet ou email
    private String seatAvailable;
    private double price;
    private Double note;           // moyenne calculée
    private String duree;

    private List<String> jour;

    private List<String> avis; 
}
