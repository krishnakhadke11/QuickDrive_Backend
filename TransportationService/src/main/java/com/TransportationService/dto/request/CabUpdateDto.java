package com.TransportationService.dto.request;

import lombok.Data;

@Data
public class CabUpdateDto {
    private int id;

    private String registerNo;

    private int seatingCapacity;

    private String color;

    private String model;

    private UserIdDto user;
}
