package com.example.wdpai2backend.dto;
import jakarta.validation.constraints.NotEmpty;


public class LoginDto {
    @NotEmpty
    String email;
    @NotEmpty
    String password;

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
