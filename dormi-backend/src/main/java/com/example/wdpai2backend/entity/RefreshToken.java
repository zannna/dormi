package com.example.wdpai2backend.entity;

import io.jsonwebtoken.Claims;
import jakarta.persistence.*;

import java.util.Date;

@Entity(name="refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_ref;

    private String refreshToken;
    private Date expiration;

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private AppUser appUser;


    public RefreshToken(String refreshToken, AppUser appUser) {
        this.refreshToken = refreshToken;
        this.appUser = appUser;
    }

    public RefreshToken() {

    }
    //CHYBA NA ODWRÓT
    public boolean isTokenValid() {
        return !expiration.before(new Date());
    }
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public void setId_ref(Long id_ref) {
        this.id_ref = id_ref;
    }

    public Long getId_ref() {
        return id_ref;
    }
}
