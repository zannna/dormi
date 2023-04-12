package com.example.wdpai2backend.entity;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name="app_user")
public class AppUser  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
    @NonNull
    private String email;
    private String username;
    private String surname;
    private String university;
    private String dormitory;
    private Integer room;
    @NonNull
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="users_authorities", joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_auth"))
    private Set<Authority> authorities = new HashSet<>();


    public AppUser() {
    }

    public AppUser(String email, String password, String username, String surname, String university, String dormitory, Integer room, Set<Authority> authorities) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.surname = surname;
        this.university = university;
        this.dormitory = dormitory;
        this.room = room;
        this.authorities = authorities;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public void setId_user(Long use_id) {
        this.id_user = use_id;
    }


    @NonNull
    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    @NonNull
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true; }

    @NonNull
    public String getSurname() {
        return surname;
    }

    public String getUniversity() {
        return university;
    }

    public String getDormitory() {
        return dormitory;
    }

    public Integer getRoom() {
        return room;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

}
