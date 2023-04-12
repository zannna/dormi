package com.example.wdpai2backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponseDto
{
    public String token;
    private String tokenType = "Bearer ";
    @JsonProperty("refresh_token")
    private String refreshToken;

    public AuthResponseDto(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

}