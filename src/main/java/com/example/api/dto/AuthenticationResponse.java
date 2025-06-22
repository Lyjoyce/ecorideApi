package com.example.api.dto;

public class AuthenticationResponse {
    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public AuthenticationResponse(String jwtToken, ActorLoginResponse response) {
		// TODO Auto-generated constructor stub
	}

	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}



