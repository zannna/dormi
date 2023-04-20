package com.example.wdpai2backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="problems")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_problem;
//    @NonNull
//    private int numberOfRoom;
    @NonNull
    private String description;

    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_dorm", nullable = false)
    @JsonIgnore
    private Dormitory dormitory;

    private LocalDateTime problem_date;

    public Long getId_problem() {
        return id_problem;
    }

    public Problem() {
    }

    public Problem(  @NonNull String description, Integer status, AppUser appUser, Dormitory dormitory,  LocalDateTime problem_date) {
//        this.numberOfRoom = numberOfRoom;
        this.description = description;
        this.status = status;
        this.appUser = appUser;
        this.dormitory = dormitory;
        this.problem_date = problem_date;
    }

    public void setId_problem(Long id_user) {
        this.id_problem = id_user;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Dormitory getDormitory() {
        return dormitory;
    }

    public void setDormitory(Dormitory dormitory) {
        this.dormitory = dormitory;
    }

    public LocalDateTime getProblem_date() {
        return problem_date;
    }

    public void setProblem_date(LocalDateTime problem_date) {
        this.problem_date = problem_date;
    }

}
