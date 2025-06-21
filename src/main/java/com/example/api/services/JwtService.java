package com.example.api.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.api.entities.Actor;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(Actor actor) {
        Map<String, Object> claims = new HashMap<>();

        //Convertit les rôles en chaîne de caractères : "PASSAGER,CONDUCTEUR"
        String roles = actor.getRoles() != null && !actor.getRoles().isEmpty()
                ? actor.getRoles().stream()
                        .map(role -> role.getName())
                        .collect(Collectors.joining(","))
                : "UNKNOWN";

        claims.put("roles", roles);
        claims.put("email", actor.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(actor.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
