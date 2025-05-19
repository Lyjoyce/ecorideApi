package com.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcorideApiApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(EcorideApiApplication.class);
		app.setAdditionalProfiles("prod", "secrets");
		app.run(args);
	}
}

