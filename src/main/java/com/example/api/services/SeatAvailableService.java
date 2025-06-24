package com.example.api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.dto.SeatAvailableRequestDTO;
import com.example.api.dto.SeatAvailableReservationDTO;
import com.example.api.entities.Actor;
import com.example.api.entities.Carpooling;
import com.example.api.entities.SeatAvailable;
import com.example.api.mappers.SeatAvailableMapper;
import com.example.api.repositories.ActorRepository;
import com.example.api.repositories.CarpoolingRepository;
import com.example.api.repositories.SeatAvailableRepository;

@Service
public class SeatAvailableService {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private CarpoolingRepository carpoolingRepository;

    @Autowired
    private SeatAvailableRepository seatAvailableRepository;
    
    public List<Carpooling> getAllCarpoolingsWithAvailableSeats() {
        return carpoolingRepository.findAll()
                .stream()
                .filter(c -> c.getSeatAvailable() > 0)
                .collect(Collectors.toList());
    }

    public SeatAvailable reserveSeat(SeatAvailableRequestDTO dto) {
        Actor passager = actorRepository.findById(dto.getPassagerId())
                .orElseThrow(() -> new RuntimeException("Passager introuvable"));
        
        Carpooling carpooling = carpoolingRepository.findById(dto.getCarpoolingId())
                .orElseThrow(() -> new RuntimeException("Trajet introuvable"));
        
        if (carpooling.getSeatAvailable() < dto.getValue()) {
            throw new IllegalStateException("Pas assez de places disponibles.");
        }

        SeatAvailable seat = SeatAvailableMapper.fromDTO(dto);
        seat.setPassager(passager);
        seat.setCarpooling(carpooling);

        return seatAvailableRepository.save(seat);
    }
    
    public SeatAvailable createSeat(SeatAvailableRequestDTO dto) {
        SeatAvailable seat = new SeatAvailable();

        seat.setFromCity(dto.getFromCity());
        seat.setToCity(dto.getToCity());
        seat.setDepartureDate(dto.getDepartureDate());
        seat.setDepartureTime(dto.getDepartureTime());
        seat.setArrivalDate(dto.getArrivalDate());
        seat.setArrivalTime(dto.getArrivalTime());
        seat.setValue(dto.getValue());

        // Récupérer les entités à partir des ID du DTO
        Actor passager = actorRepository.findById(dto.getPassagerId())
            .orElseThrow(() -> new RuntimeException("Passager not found"));

        Carpooling carpooling = carpoolingRepository.findById(dto.getCarpoolingId())
            .orElseThrow(() -> new RuntimeException("Carpooling not found"));

        seat.setPassager(passager);
        seat.setCarpooling(carpooling);

        return seatAvailableRepository.save(seat);
    }
    
    public SeatAvailableReservationDTO getReservationDetails(Long reservationId) {
        SeatAvailable seat = seatAvailableRepository.findById(reservationId)
            .orElseThrow(() -> new RuntimeException("Réservation introuvable"));

        SeatAvailableReservationDTO dto = new SeatAvailableReservationDTO();
        

        dto.setReservationId(seat.getId());
        dto.setCarpoolingId(seat.getCarpooling().getId());
        dto.setFromCity(seat.getFromCity());
        dto.setToCity(seat.getToCity());
        dto.setDepartureDate(seat.getDepartureDate());
        dto.setDepartureTime(seat.getDepartureTime());
        dto.setArrivalDate(seat.getArrivalDate());
        dto.setArrivalTime(seat.getArrivalTime());
        dto.setValue(seat.getValue());

        dto.setPassagerId(seat.getPassager().getId());
        dto.setPassagerName(seat.getPassager().getFirstname() + " " + seat.getPassager().getLastname());
        dto.setPassagerEmail(seat.getPassager().getEmail());

        dto.setConducteurName(seat.getCarpooling().getConducteur().getFirstname() + " " + seat.getCarpooling().getConducteur().getLastname());
        dto.setConducteurEmail(seat.getCarpooling().getConducteur().getEmail());

        dto.setVoitureMarque(seat.getCarpooling().getVoiture().getMarque());
        dto.setVoitureModele(seat.getCarpooling().getVoiture().getModele());

        return dto;
    }  
}
