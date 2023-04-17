package com.example.wdpai2backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public class RegisterDto {
    @NotEmpty
    String email;
    @NotEmpty
    String password;
    @NotEmpty
    String username;
    @NotEmpty
    String surname;
    @NotEmpty
    String university;
    @NotEmpty
    String dormitory;
    @NotNull
    Integer room;

    public RegisterDto(String email, String password, String username, String surname, String university, String dormitory, Integer room) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.surname = surname;
        this.university = university;
        this.dormitory = dormitory;
        this.room = room;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }
}
