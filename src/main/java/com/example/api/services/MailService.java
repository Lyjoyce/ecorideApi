package com.example.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTestEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@ecoride.com"); // adresse fictive ou domaine validé
        message.setTo(to);
        message.setSubject("✅ Test Mailjet via Spring Boot");
        message.setText("Bonjour ! Ceci est un email de test envoyé depuis Spring Boot via Mailjet SMTP.");

        mailSender.send(message);
    }
}
