package com.TransportationService.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalTime;

@Data
public class DriverUpdateDto {
    private int id;

    @Schema(description = "State code, Rto code, Issued Year, and the license number should be present", example = "MH12 20241234567")
    @Pattern(regexp = "^([A-Z]{2}[0-9]{2} )((19|20)[0-9]{2})[0-9]{7}$",message = "Please Enter Valid Driving License")
    private String driversLicense;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSS")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSS")
    private LocalTime endTime;

    private UserUpdateDetailsDto user;
}
