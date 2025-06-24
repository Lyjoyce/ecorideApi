package com.example.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.SeatAvailableRequestDTO;
import com.example.api.dto.SeatAvailableReservationDTO;
import com.example.api.entities.Carpooling;
import com.example.api.entities.SeatAvailable;
import com.example.api.services.SeatAvailableService;

@RestController
@RequestMapping("/api/seats")

public class SeatAvailableController {

    @Autowired
    private SeatAvailableService seatAvailableService;
    
    @GetMapping
    public List<Carpooling> getAll() {
        return seatAvailableService.getAllCarpoolingsWithAvailableSeats();
    }



    @PostMapping("/reserve")
    public ResponseEntity<SeatAvailable> reserveSeat(@RequestBody SeatAvailableRequestDTO dto) {
        SeatAvailable seat = seatAvailableService.reserveSeat(dto);
        return ResponseEntity.ok(seat);
    }
    
    @GetMapping("/reservation/{id}")
    public ResponseEntity<SeatAvailableReservationDTO> getReservationDetails(@PathVariable Long id) {
        SeatAvailableReservationDTO dto = seatAvailableService.getReservationDetails(id);
        return ResponseEntity.ok(dto);
    }
}

