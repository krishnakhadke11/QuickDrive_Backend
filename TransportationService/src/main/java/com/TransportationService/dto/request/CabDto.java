package com.TransportationService.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CabDto {
    @Schema(description = "Must be valid Register Number", example = "MH12CD1234")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}", message = "Please Valid Registration Number")
    private String registerNo;

    @Schema(description = "Either 5 or 7")
    private int seatingCapacity;

    @Pattern(regexp = "^[A-za-z ]+$", message = "Please enter valid color")
    private String color;

    @Pattern(regexp = "^[A-za-z0-9 ]+$", message = "Please enter valid model")
    private String model;

    private UserIdDto user;
}
