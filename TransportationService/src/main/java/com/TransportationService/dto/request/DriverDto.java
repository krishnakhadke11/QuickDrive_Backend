package com.TransportationService.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;

@Data
public class DriverDto {
    @Schema(description = "State code, Rto code, Issued Year, and the license number should be present", example = "MH12 20241234567")
    private String driversLicense;

    private LocalTime startTime;

    private LocalTime endTime;

    private UserDetailsDto user;
}
