package com.example.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "contacts")
public class Contact {

    @Id
    private String id;

    @NotBlank(message = "Le nom est obligatoire.")
    private String name;

    @NotBlank(message = "L'adresse email est obligatoire.")
    @Email(message = "Adresse email invalide.")
    private String email;

    @NotBlank(message = "Le message ne peut pas être vide.")
    private String message;
    
    private String reponse;



    public Contact() {}

    public Contact(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public void setReponse(String reponse2) {
		// TODO Auto-generated method stub
		
	}
}
