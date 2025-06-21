package com.example.api.controllers;

import com.example.api.dto.SeatAvailableRequestDTO;
import com.example.api.entities.SeatAvailable;
import com.example.api.services.SeatAvailableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seats")
@CrossOrigin(origins = "*")
public class SeatAvailableController {

    @Autowired
    private SeatAvailableService seatAvailableService;

    @PostMapping("/reserve")
    public ResponseEntity<SeatAvailable> reserveSeat(@RequestBody SeatAvailableRequestDTO dto) {
        SeatAvailable seat = seatAvailableService.reserveSeat(dto);
        return ResponseEntity.ok(seat);
    }
}

