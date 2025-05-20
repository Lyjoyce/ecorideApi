package com.example.api.repositories.mongo;

import com.example.api.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends MongoRepository<Contact, String> {
    //  méthodes personnalisées
}

