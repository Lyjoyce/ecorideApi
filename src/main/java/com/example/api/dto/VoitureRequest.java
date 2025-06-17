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
    private String energy;
    private List<String> options;
    private boolean ecologique;

    private String roleName;
}
