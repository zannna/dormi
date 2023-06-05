package com.example.wdpai2backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_mess;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    private AppUser appUser;

    private String message;

    private LocalDateTime mess_date;

    @Transient
    @JsonSerialize
    String owner;

    @Transient
    @JsonSerialize
    String numberOfOwner;

    public Message() {
    }

    public Message(AppUser appUser, String message, LocalDateTime mess_date) {
        this.appUser = appUser;
        this.message = message;
        this.mess_date = mess_date;
    }

    public void setId_mess(Long id_mess) {
        this.id_mess = id_mess;
    }

    public Long getId_mess() {
        return id_mess;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getMess_date() {
        return mess_date;
    }

    public void setMess_date(LocalDateTime mess_date) {
        this.mess_date = mess_date;

    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getJson() {
        return "{\"id_mess\":" + id_mess + ",\"message\":\"" + message + "\",\"owner\":\"" + owner + "\"," + "\"numberOfOwner\":" + numberOfOwner + "}";
    }

    public String getNumberOfOwner() {
        return numberOfOwner;
    }

    public void setNumberOfOwner(String numberOfOwner) {
        this.numberOfOwner = numberOfOwner;
    }
}
