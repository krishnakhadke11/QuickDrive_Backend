package com.TransportationService.dto.response;

import com.TransportationService.entity.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RideRequestWithRideDto {
    private int id;

    private String pickupLocation;

    private String pickupName;

    private String dropLocation;

    private String dropName;

    private String distance;

    private String duration;

    private int fare;

    private PaymentType paymentType;

    private SeatingCapacity seatingCapacity;

    private BookingStatus bookingStatus;

    private LocalDateTime requestTime;

    private Ride ride;
}
