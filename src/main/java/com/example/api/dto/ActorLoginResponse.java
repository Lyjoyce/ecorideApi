package com.example.api.dto;

import lombok.Data;

@Data
public class ActorLoginResponse {
    public ActorLoginResponse(Long id2, String firstname2, int credits2, String token2, String refreshToken2,
			String roleName) {
	}
	
	private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private int credits;
    private String token;
    private String refreshToken;
    private String role;
}
