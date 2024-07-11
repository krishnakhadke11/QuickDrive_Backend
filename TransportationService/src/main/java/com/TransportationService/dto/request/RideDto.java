package com.TransportationService.dto.request;


import lombok.Data;

@Data
public class RideDto {
    private String pickupLocation;

    private String dropLocation;

    private int rating;

    private int fare;

    private String distance;

    private String duration;

    private boolean booking_status;

    private int customerId;

    private int cabId;

    private int driverId;
}

