package com.TransportationService.dto.request;

import lombok.Data;

import java.time.LocalTime;

@Data
public class DriverUpdateDto {
    private int id;
    private String driversLicense;
    private LocalTime startTime;
    private LocalTime endTime;
    private UserUpdateDetailsDto user;
}
