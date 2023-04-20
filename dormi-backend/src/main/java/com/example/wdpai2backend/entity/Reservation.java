package com.example.wdpai2backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_res;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnore
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_dorm", nullable = false)
    @JsonIgnore
    private Dormitory dorm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_device", nullable = false)
    @JsonIgnore
    private Device device;

    private LocalDateTime start_date;
    private LocalDateTime end_date;

    public Reservation() {
    }

    public Reservation(AppUser appUser, Dormitory dorm, Device device, LocalDateTime start_date, LocalDateTime end_date) {
        this.appUser = appUser;
        this.dorm = dorm;
        this.device = device;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public void setId_res(Long id_res) {
        this.id_res = id_res;
    }

    public Long getId_res() {
        return id_res;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Dormitory getDorm() {
        return dorm;
    }

    public void setDorm(Dormitory dorm) {
        this.dorm = dorm;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime date) {
        this.start_date = date;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }
}
