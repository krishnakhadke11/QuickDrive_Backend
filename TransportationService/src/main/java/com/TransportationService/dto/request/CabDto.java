package com.TransportationService.dto.request;

import lombok.Data;

@Data
public class CabDto {
    private String registerNo;

    private int seatingCapacity;

    private String color;

    private String model;

    private UserIdDto user;
}
