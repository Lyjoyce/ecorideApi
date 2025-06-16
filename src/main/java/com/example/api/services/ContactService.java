package com.example.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.api.model.Contact;
import com.example.api.repositories.mongo.ContactRepository;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final JavaMailSender mailSender; 

    // ✅ Constructeur avec les deux dépendances
    @Autowired
    public ContactService(ContactRepository contactRepository, JavaMailSender mailSender) {
        this.contactRepository = contactRepository;
        this.mailSender = mailSender;
    }

    public Contact saveContact(Contact contact) {
        Contact saved = contactRepository.save(contact);

        // ✅ Préparation de l’email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("contact.ecoride@gmail.com");
        message.setSubject("📩 Nouveau message de " + contact.getName());
        message.setText(
            "Nom : " + contact.getName() + "\n" +
            "Email : " + contact.getEmail() + "\n\n" +
            "Message :\n" + contact.getMessage()
        );

        mailSender.send(message); // ✅ Envoi réel

        return saved;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Optional<Contact> getContactById(String id) {
        return contactRepository.findById(id);
    }

    public void deleteContact(String id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
        }
    }
}
