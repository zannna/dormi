package com.example.wdpai2backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_device;
    private String name_device;
    private boolean work;
    private Integer number;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_dorm", nullable = false)
    @JsonIgnore
    private Dormitory dormitory;


    public Device(String name, boolean work) {
        this.name_device = name;
        this.work = work;
    }

    public Device() {


    }

    public void setId_device(Long id) {
        this.id_device = id;
    }

    public Long getId_device() {
        return id_device;
    }

    public String getName_device() {
        return name_device;
    }

    public void setName_device(String name_device) {
        this.name_device = name_device;
    }

    public boolean isWork() {
        return work;
    }

    public void setWork(boolean work) {
        this.work = work;
    }

    public Dormitory getDormitory() {
        return dormitory;
    }

    public void setDormitory(Dormitory dormitory) {
        this.dormitory = dormitory;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}
