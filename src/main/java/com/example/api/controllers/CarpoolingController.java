package com.example.api.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.dto.CarpoolingDTO;
import com.example.api.dto.CarpoolingResponseDTO;
import com.example.api.dto.ReservationRequestDTO;
import com.example.api.dto.SeatAvailableReservationDTO;
import com.example.api.entities.Actor;
import com.example.api.entities.Carpooling;
import com.example.api.repositories.ActorRepository;
import com.example.api.repositories.CarpoolingRepository;
import com.example.api.security.JwtUtil;
import com.example.api.services.CarpoolingService;

@RestController
@RequestMapping("/api/v1/carpooling")
public class CarpoolingController {

    @Autowired
    private CarpoolingRepository carpoolingRepository;

    @Autowired
    private CarpoolingService carpoolingService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ActorRepository actorRepository;

    private boolean isConducteur(String token) {
        String email = jwtUtil.extractUsername(token);
        Actor actor = actorRepository.findByEmail(email).orElse(null);
        return actor != null && actor.getRoles().stream()
                .anyMatch(r -> r.getName().equals("ROLE_CONDUCTEUR"));
    }

    @PostMapping("/create")
    public ResponseEntity<?> proposerTrajet(@RequestBody CarpoolingDTO carpoolingDTO,
                                            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!isConducteur(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Accès réservé aux conducteurs.");
        }
        String email = jwtUtil.extractUsername(token);
        return carpoolingService.proposerTrajet(email, carpoolingDTO);
    }

    @GetMapping
    public List<Carpooling> getAll() {
        return carpoolingRepository.findAll()
            .stream()
            .filter(c -> c.getSeatAvailable() > 0)
            .collect(Collectors.toList());
    }

    @GetMapping("/example")
    public ResponseEntity<CarpoolingResponseDTO> exampleCarpooling() {
        CarpoolingResponseDTO dto = new CarpoolingResponseDTO();

        dto.setFromCity("Rouen");
        dto.setToCity("Deauville : 14200 gare de Deauville-Trouville");
        dto.setDepartureDate(LocalDate.of(2025, 5, 21));
        dto.setDepartureTime(LocalTime.of(9, 0));
        dto.setArrivalDate(LocalDate.of(2025, 5, 21));
        dto.setArrivalTime(LocalTime.of(10, 15));
        dto.setOption("voyage ecologique");
        dto.setEnergy("electrique");
        dto.setConducteur("Julia Rock");
        dto.setSeatAvailable("3");
        dto.setPrice(4.0);
        dto.setNote(5.0);
        dto.setDuree("1H15");
        dto.setJour(Arrays.asList("lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimanche"));

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/historique/{actorId}")
    public ResponseEntity<List<Carpooling>> getHistorique(@PathVariable Long actorId) {
        Actor actor = actorRepository.findById(actorId).orElse(null);
        if (actor == null) {
            return ResponseEntity.notFound().build();
        }

        List<Carpooling> historique = carpoolingRepository
                .findByConducteurOrPassagersContains(actor, actor);

        return ResponseEntity.ok(historique);
    }

    @PutMapping("/{id}/annuler")
    public ResponseEntity<?> annulerCarpooling(@PathVariable Long id, Principal principal) {
        Carpooling carpooling = carpoolingRepository.findById(id).orElse(null);
        if (carpooling == null) {
            return ResponseEntity.notFound().build();
        }

        String email = principal.getName();
        Actor acteurConnecte = actorRepository.findByEmail(email).orElse(null);

        if (acteurConnecte == null || !carpooling.getConducteur().getId().equals(acteurConnecte.getId())) {
            return ResponseEntity.status(403).body("Vous n'êtes pas autorisé à annuler ce covoiturage.");
        }

        carpooling.setAnnule(true);
        carpoolingRepository.save(carpooling);

        return ResponseEntity.ok("Covoiturage annulé avec succès.");
    }
    
    @PostMapping("/reserve")
    public ResponseEntity<?> reserverPlace(@RequestBody ReservationRequestDTO request,
                                           @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractUsername(token);

        SeatAvailableReservationDTO response = carpoolingService.reserverPlace(email, request);

        if (response == null) {
            return ResponseEntity.badRequest().body("Impossible de réserver : place indisponible ou déjà réservée.");
        }

        return ResponseEntity.ok(response);
    }

}
