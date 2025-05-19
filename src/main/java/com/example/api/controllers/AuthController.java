package com.example.api.controllers;

import com.example.api.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

       
        if ("test@example.com".equals(email) && "1234".equals(password)) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}

