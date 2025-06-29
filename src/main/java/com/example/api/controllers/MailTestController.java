package com.example.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.api.dto.MailRequest;
import com.example.api.services.MailService;

@RestController
@RequestMapping("/api/test-mail")
public class MailTestController {

    private final MailService mailService;

    public MailTestController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping
    public ResponseEntity<String> sendTestMail(@RequestBody MailRequest request) {
        try {
            mailService.sendEmail(request.getTo(), request.getSubject(), request.getMessage());
            return ResponseEntity.ok("✅ Email envoyé à " + request.getTo());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Échec : " + e.getMessage());
        }
    }
}
