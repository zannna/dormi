package com.example.wdpai2backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name="dormitory")
public class Dormitory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_dorm;
    private String dorm_name;


    public void setId_dorm(Long id_dorm) {
        this.id_dorm = id_dorm;
    }


    public Long getId_dorm() {
        return id_dorm;
    }



}
