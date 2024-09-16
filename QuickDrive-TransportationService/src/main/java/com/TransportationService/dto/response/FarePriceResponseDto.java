package com.TransportationService.dto.response;

import lombok.Data;

@Data
public class FarePriceResponseDto {
    private String pickupLocation;

    private String dropLocation;

    private String distance;

    private String duration;

    private int fiveSeaterFare;

    private int sevenSeaterFare;


}
