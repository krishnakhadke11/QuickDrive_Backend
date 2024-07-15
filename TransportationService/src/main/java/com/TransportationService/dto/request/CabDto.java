package com.TransportationService.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CabDto {
    @Schema(description = "Must be valid Register Number", example = "MH12CD1234")
    private String registerNo;

    @Schema(description = "Either 5 or 7")
    private int seatingCapacity;

    private String color;

    private String model;

    private UserIdDto user;
}
