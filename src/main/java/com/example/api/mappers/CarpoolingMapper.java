package com.example.api.mappers;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.api.dto.CarpoolingDTO;
import com.example.api.entities.Actor;
import com.example.api.entities.Avis;
import com.example.api.entities.Carpooling;
import com.example.api.repositories.ActorRepository;
import com.example.api.repositories.VoitureRepository;

@Component
public class CarpoolingMapper {

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private ActorRepository actorRepository;

    // Convertit un DTO en entité
    public Carpooling toEntity(CarpoolingDTO dto) {
        Carpooling carpooling = new Carpooling();

        carpooling.setFromCity(dto.getFromCity());
        carpooling.setToCity(dto.getToCity());
        carpooling.setDepartureLocation(dto.getDepartureLocation());
        carpooling.setArrivalLocation(dto.getArrivalLocation());

        carpooling.setDepartureDate(dto.getDepartureDate());
        carpooling.setDepartureTime(dto.getDepartureTime());
        carpooling.setArrivalDate(dto.getArrivalDate());
        carpooling.setArrivalTime(dto.getArrivalTime());

        carpooling.setPrice(dto.getPrice());
        carpooling.setEnergy(dto.isEnergy());
        carpooling.setNote(dto.getNote());
        carpooling.setJour(dto.getJour());

        // Calcul de la durée en minutes (gestion des traversées de minuit)
        if (dto.getDepartureTime() != null && dto.getArrivalTime() != null) {
            long duree = Duration.between(dto.getDepartureTime(), dto.getArrivalTime()).toMinutes();
            if (duree < 0) duree += 24 * 60;
            carpooling.setDuree((int) duree);
        }

        // Lien vers la voiture
        if (dto.getVoitureId() != null) {
            voitureRepository.findById(dto.getVoitureId()).ifPresent(carpooling::setVoiture);
        }

        // Lien vers le conducteur
        if (dto.getConducteurId() != null) {
            actorRepository.findById(dto.getConducteurId()).ifPresent(carpooling::setConducteur);
        }

        return carpooling;
    }

    // Convertit une entité en DTO
    public CarpoolingDTO toDto(Carpooling entity) {
        CarpoolingDTO dto = new CarpoolingDTO();

        dto.setId(entity.getId());
        dto.setFromCity(entity.getFromCity());
        dto.setToCity(entity.getToCity());
        dto.setDepartureLocation(entity.getDepartureLocation());
        dto.setArrivalLocation(entity.getArrivalLocation());

        dto.setDepartureDate(entity.getDepartureDate());
        dto.setDepartureTime(entity.getDepartureTime());
        dto.setArrivalDate(entity.getArrivalDate());
        dto.setArrivalTime(entity.getArrivalTime());

        dto.setSeatAvailable(entity.getSeatAvailable());
        dto.setPrice(entity.getPrice());
        dto.setEnergy(entity.isEnergy());
        dto.setDuree(entity.getDuree());
        dto.setNote(entity.getNote());
        dto.setJour(entity.getJour());

        if (entity.getConducteur() != null) {
            Actor conducteur = entity.getConducteur();
            dto.setConducteur(conducteur.getFirstname() + " " + conducteur.getLastname());
            dto.setConducteurId(conducteur.getId());

            // Avis validés uniquement
            List<Avis> avisList = conducteur.getAvisRecus().stream()
                .filter(Avis::isValide)
                .collect(Collectors.toList());

            if (!avisList.isEmpty()) {
                double moyenne = avisList.stream()
                    .mapToInt(Avis::getNote)
                    .average()
                    .orElse(0);
                dto.setNote(Math.round(moyenne * 10) / 10.0);

                List<String> avisMessages = avisList.stream()
                    .map(Avis::getAvis)
                    .collect(Collectors.toList());
                dto.setAvis(avisMessages);
            } else {
                dto.setNote(null); // pas de note
                dto.setAvis(List.of());
            }
        }

        if (entity.getVoiture() != null) {
            dto.setVoitureId(entity.getVoiture().getId());
        }

        return dto;
    }
}
