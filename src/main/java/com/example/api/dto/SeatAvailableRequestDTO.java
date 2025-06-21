package com.example.api.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.api.entities.Actor;
import com.example.api.entities.Carpooling;

import lombok.Data;

@Data
public class SeatAvailableRequestDTO {
    private String fromCity;
    private String toCity;

    private LocalDate departureDate;
    private LocalTime departureTime;

    private LocalDate arrivalDate;
    private LocalTime arrivalTime;

    private int value; // nombre de places à réserver
    
    private Long passagerId;    
    private Long carpoolingId;  

   }