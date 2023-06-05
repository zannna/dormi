package com.example.wdpai2backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ResponseReservationDto {
    LocalDate[][] weeks;
    List<Integer> devicesNumbers;
    List<List<List<LocalDateTime[]>>> reservations;

    public ResponseReservationDto(LocalDate[][] weeks, List<Integer> numberOfDevice, List<List<List<LocalDateTime[]>>> reservations) {
        this.weeks = weeks;
        this.devicesNumbers = numberOfDevice;
        this.reservations = reservations;
    }

    public LocalDate[][] getWeeks() {
        return weeks;
    }

    public void setWeeks(LocalDate[][] weeks) {
        this.weeks = weeks;
    }

    public List<Integer> getNumberOfDevice() {
        return devicesNumbers;
    }

    public void setNumberOfDevice(List<Integer> numberOfDevice) {
        this.devicesNumbers = numberOfDevice;
    }

    public List<List<List<LocalDateTime[]>>> getReservations() {
        return reservations;
    }

    public void setReservations(List<List<List<LocalDateTime[]>>> reservations) {
        this.reservations = reservations;
    }
}
