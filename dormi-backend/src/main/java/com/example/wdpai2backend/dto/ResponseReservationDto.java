package com.example.wdpai2backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ResponseReservationDto {
    Integer numberOfDevice;
    List<LocalDateTime> resevations;

    public ResponseReservationDto(Integer numberOfDevice, List<LocalDateTime> resevations) {
        this.numberOfDevice = numberOfDevice;
        this.resevations = resevations;
    }

    public Integer getNumberOfDevice() {
        return numberOfDevice;
    }

    public void setNumberOfDevice(Integer numberOfDevice) {
        this.numberOfDevice = numberOfDevice;
    }

    public List<LocalDateTime> getResevations() {
        return resevations;
    }

    public void setResevations(List<LocalDateTime> resevations) {
        this.resevations = resevations;
    }
}
