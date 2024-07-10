package com.TransportationService.dto.response;

import lombok.Data;

@Data
public class FarePriceResponseDto {
    private String pickupLocation;

    private String dropLocation;

    private int seatingCapacity;

    private String distance;

    private String duration;

    private int fare;

}
