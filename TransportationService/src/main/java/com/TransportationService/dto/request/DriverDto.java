package com.TransportationService.dto.request;

import lombok.Data;

import java.time.LocalTime;

@Data
public class DriverDto {
    private String driversLicense;
    private LocalTime startTime;
    private LocalTime endTime;
    private UserDetailsDto user;
}
