package com.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.api.entities.Actor;

@SpringBootApplication
public class EcorideApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcorideApiApplication.class, args);
		Actor a = new Actor();	
		
	}

}
