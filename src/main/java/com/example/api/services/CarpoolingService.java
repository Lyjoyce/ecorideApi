package com.example.api.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.api.dto.CarpoolingDTO;
import com.example.api.dto.CarpoolingResponseDTO;
import com.example.api.dto.ReservationRequestDTO;
import com.example.api.dto.SeatAvailableReservationDTO;
import com.example.api.entities.Actor;
import com.example.api.entities.Carpooling;
import com.example.api.entities.Voiture;
import com.example.api.repositories.ActorRepository;
import com.example.api.repositories.CarpoolingRepository;
import com.example.api.repositories.VoitureRepository;

@Service
public class CarpoolingService {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private CarpoolingRepository carpoolingRepository;

    public ResponseEntity<?> proposerTrajet(String email, CarpoolingDTO dto) {
        Optional<Actor> optionalActor = actorRepository.findByEmail(email);
        if (optionalActor.isEmpty()) {

        }
            if (dto.getPrice() <= 0) {
                return ResponseEntity.badRequest().body("Le prix du trajet doit être supérieur à 0.");
            }

        Actor actor = optionalActor.get();

        // Vérifier rôle conducteur
        boolean isDriver = actor.getRoles().stream()
                .anyMatch(role -> "ROLE_CONDUCTEUR".equals(role.getName()));
        if (!isDriver) {
            return ResponseEntity.status(403).body("Vous devez être conducteur pour proposer un trajet.");
        }

        // Vérifier voiture et appartenance
        Optional<Voiture> optionalVoiture = voitureRepository.findById(dto.getVoitureId());
        if (optionalVoiture.isEmpty()) {
            return ResponseEntity.status(404).body("Voiture non trouvée.");
        }
        Voiture voiture = optionalVoiture.get();

        if (voiture.getConducteurActor() == null || !voiture.getConducteurActor().getId().equals(actor.getId())) {
            return ResponseEntity.status(403).body("Cette voiture ne vous appartient pas.");
        }

        // Créer le trajet
        Carpooling carpooling = new Carpooling();

        carpooling.setFromCity(dto.getFromCity());
        carpooling.setToCity(dto.getToCity());
        carpooling.setDepartureLocation(dto.getDepartureLocation());
        carpooling.setArrivalLocation(dto.getArrivalLocation());
        carpooling.setDepartureDate(dto.getDepartureDate());
        carpooling.setDepartureTime(dto.getDepartureTime());
        carpooling.setArrivalDate(dto.getArrivalDate());
        carpooling.setArrivalTime(dto.getArrivalTime());
        carpooling.setEnergy(dto.isEnergy());
        carpooling.setPreferences(dto.getPreferences());
        carpooling.setPrice(dto.getPrice());
        carpooling.setVoiture(voiture);
        carpooling.setConducteur(actor);

     // Définir le nombre de places disponibles à la création = nombre total de la voiture
        carpooling.setSeatAvailable(voiture.getNbPlacesDisponibles());

        carpoolingRepository.save(carpooling);

        return ResponseEntity.ok("Covoiturage créé avec succès.");
    }

    public CarpoolingResponseDTO toResponseDTO(Carpooling carpooling) {
        CarpoolingResponseDTO dto = new CarpoolingResponseDTO();

        dto.setFromCity(carpooling.getFromCity());
        dto.setToCity(carpooling.getToCity());
        dto.setDepartureDate(carpooling.getDepartureDate());
        dto.setDepartureTime(carpooling.getDepartureTime());
        dto.setArrivalDate(carpooling.getArrivalDate());
        dto.setArrivalTime(carpooling.getArrivalTime());

        dto.setPreferences(carpooling.getPreferences());
        dto.setEnergy(carpooling.isEnergy() ? "electrique" : "essence");

        Actor conducteur = carpooling.getConducteur();
        dto.setConducteur(conducteur != null ? conducteur.getFirstname() + " " + conducteur.getLastname() : "Inconnu");

     // Afficher seatAvailable seulement si > 0
        if (carpooling.getSeatAvailable() != null && carpooling.getSeatAvailable() > 0) {
            dto.setSeatAvailable(String.valueOf(carpooling.getSeatAvailable()));
        } else {
            dto.setSeatAvailable("Complet");
        }

        // Price est obligatoire
        dto.setPrice(carpooling.getPrice()); //type double

        // Note : peut être null
        dto.setNote(carpooling.getNote());

        int dureeMinutes = carpooling.getDuree();
        int heures = dureeMinutes / 60;
        int minutes = dureeMinutes % 60;
        dto.setDuree(heures + "H" + (minutes < 10 ? "0" : "") + minutes); // Ex: 1H05 ou 2H15

     // Calcul dynamique du jour depuis departureDate
        if (carpooling.getDepartureDate() != null) {
            java.time.DayOfWeek dayOfWeek = carpooling.getDepartureDate().getDayOfWeek();
            String jour = dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.FRENCH);
            dto.setJour(java.util.Collections.singletonList(jour));
        } else {
            dto.setJour(java.util.Collections.singletonList("Non spécifié"));
        }
        return dto;             
        }
        
        public SeatAvailableReservationDTO reserverPlace(String email, ReservationRequestDTO request) {
            Optional<Actor> optionalPassager = actorRepository.findByEmail(email);
            if (optionalPassager.isEmpty()) return null;
            Actor passager = optionalPassager.get();

            Optional<Carpooling> optionalTrajet = carpoolingRepository.findById(request.getCarpoolingId());
            if (optionalTrajet.isEmpty()) return null;
            Carpooling trajet = optionalTrajet.get();

            if (trajet.getSeatAvailable() <= 0) return null;

            // Évite les réservations en double
            if (trajet.getPassagers().contains(passager)) {
                return null; // déjà réservé
            }

            // Réservation
            trajet.getPassagers().add(passager);
            trajet.setSeatAvailable(trajet.getSeatAvailable() - 1);
            carpoolingRepository.save(trajet);

            // Construction du récapitulatif
            SeatAvailableReservationDTO dto = new SeatAvailableReservationDTO();
            dto.setCarpoolingId(trajet.getId());
            dto.setFromCity(trajet.getFromCity());
            dto.setToCity(trajet.getToCity());
            dto.setDepartureDate(trajet.getDepartureDate());
            dto.setDepartureTime(trajet.getDepartureTime());
            dto.setArrivalDate(trajet.getArrivalDate());
            dto.setArrivalTime(trajet.getArrivalTime());
            dto.setValue(1); // une seule place réservée

            dto.setPassagerId(passager.getId());
            dto.setPassagerName(passager.getFirstname() + " " + passager.getLastname());
            dto.setPassagerEmail(passager.getEmail());

            Actor conducteur = trajet.getConducteur();
            dto.setConducteurName(conducteur.getFirstname() + " " + conducteur.getLastname());
            dto.setConducteurEmail(conducteur.getEmail());

            dto.setVoitureMarque(trajet.getVoiture().getMarque());
            dto.setVoitureModele(trajet.getVoiture().getModele());
  
        return dto;
    }
        
        public Map<LocalDate, Long> countByDate() {
            return carpoolingRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                    Carpooling::getDepartureDate, 
                    Collectors.counting()
                ));
        }

     // Retourne un Map <Date, Somme des crédits (prix) des trajets ce jour>
        public Map<LocalDate, Double> creditsByDate() {
            List<Carpooling> carpoolings = carpoolingRepository.findAll().stream()
                .filter(c -> !c.isAnnule()) // ignore les trajets annulés
                .collect(Collectors.toList());

            return carpoolings.stream()
                .collect(Collectors.groupingBy(
                    Carpooling::getDepartureDate,
                    Collectors.summingDouble(Carpooling::getPrice)
                ));
        }

        // Somme totale des crédits (prix) de tous les trajets non annulés
        public Double getTotalCredits() {
            return carpoolingRepository.findAll().stream()
                .filter(c -> !c.isAnnule())
                .mapToDouble(Carpooling::getPrice)
                .sum();
        }
    }

