package com.example.api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.api.model.Contact;

public interface ContactRepository extends MongoRepository<Contact, String> {
    // méthodes 
}


