package com.example.api.controllers;


import com.example.api.model.Contact;
import com.example.api.services.ContactService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*")
public class ContactControllers {

    private final ContactService contactService;

    public ContactControllers(ContactService contactService) {
        this.contactService = contactService;
    }
    
    @PostMapping
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) {
        Contact saved = contactService.saveContact(contact);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        List<Contact> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }
}
