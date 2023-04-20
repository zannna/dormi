package com.example.wdpai2backend.dto;


public class ReservationDto {
    String device;
    Integer numberOfDevice;
    String startDate;
    String endDate;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getNumberOfDevice() {
        return numberOfDevice;
    }

    public void setNumberOfDevice(Integer numberOfDevice) {
        this.numberOfDevice = numberOfDevice;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
