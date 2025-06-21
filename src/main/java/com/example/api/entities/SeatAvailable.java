package com.example.api.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatAvailable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromCity;
    private String toCity;

    private LocalDate departureDate;
    private LocalTime departureTime;

    private LocalDate arrivalDate;
    private LocalTime arrivalTime;

    @Column(name = "disponibility", nullable = false)
    private int value; // nombre de places réservées ou demandées

    @ManyToOne
    @JoinColumn(name = "passager_id", nullable = false)
    private Actor passager;

    @ManyToOne
    @JoinColumn(name = "carpooling_id", nullable = false)
    private Carpooling carpooling;

}
