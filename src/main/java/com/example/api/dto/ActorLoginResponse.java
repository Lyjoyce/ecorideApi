package com.example.api.dto;

import lombok.Data;

@Data
public class ActorLoginResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
}
