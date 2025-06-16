package com.example.api.controllers;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.model.Contact;
import com.example.api.services.ContactService;

import jakarta.validation.Valid;

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
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable String id) {
        return contactService.getContactById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable String id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/repondre")
    public ResponseEntity<Contact> repondreAuContact(@PathVariable String id, @RequestBody String reponse) {
        return contactService.getContactById(id).map(contact -> {
            contact.setReponse(reponse);
            Contact updated = contactService.saveContact(contact); // ou updateContact()
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }



}
