package com.example.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.api.services.MailService;

@RestController
@RequestMapping("/api/test-mail")
public class MailTestController {

    @Autowired
    private MailService mailService;

    @GetMapping
    public String sendTestMail(@RequestParam String to) {
        try {
            mailService.sendTestEmail(to);
            return "✅ Email envoyé avec succès à " + to;
        } catch (Exception e) {
            return "❌ Échec de l'envoi : " + e.getMessage();
        }
    }
}

