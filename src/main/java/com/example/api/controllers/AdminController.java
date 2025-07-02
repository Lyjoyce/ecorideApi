package com.example.api.controllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.api.dto.AuthenticationResponse;
import com.example.api.dto.RegisterRequest;
import com.example.api.services.AuthenticationService;
import com.example.api.services.ActorService;
import com.example.api.services.CarpoolingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminController {

    private final AuthenticationService authenticationService;
    private final CarpoolingService carpoolingService;
    private final ActorService actorService;

    @PostMapping("/admin/create-employe")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AuthenticationResponse> createEmploye(@RequestBody RegisterRequest request) {
        request.setRole("ROLE_EMPLOYE");
        return ResponseEntity.ok(authenticationService.register(request));
    }
    
    @GetMapping("/admin/stats/credits-per-day")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<LocalDate, Double>> getCreditsPerDay() {
        Map<LocalDate, Double> stats = carpoolingService.creditsByDate();
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/admin/stats/total-credits")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Double> getTotalCredits() {
        Double total = carpoolingService.getTotalCredits();
        return ResponseEntity.ok(total);
    }
    
    @PutMapping("/admin/suspend/{actorId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> suspendActor(@PathVariable Long actorId) {
        actorService.suspend(actorId);
        return ResponseEntity.ok("Compte suspendu");
    }
}
