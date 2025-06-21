package com.example.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.dto.SeatAvailableRequestDTO;
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

    public SeatAvailable reserveSeat(SeatAvailableRequestDTO dto) {
        Actor passager = actorRepository.findById(dto.getPassagerId())
                .orElseThrow(() -> new RuntimeException("Passager introuvable"));

        Carpooling carpooling = carpoolingRepository.findById(dto.getCarpoolingId())
                .orElseThrow(() -> new RuntimeException("Trajet introuvable"));

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

}
