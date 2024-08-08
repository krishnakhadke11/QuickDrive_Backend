package com.TransportationService.dto.response;

import com.TransportationService.entity.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RideRequestResponseDto {
    private int id;

    private String pickupLocation;

    private String dropLocation;

    private String distance;

    private String duration;

    private int fare;

    private PaymentType paymentType;

    private SeatingCapacity seatingCapacity;

    private BookingStatus bookingStatus;

    private LocalDateTime requestTime;

    private Customer customer;
}