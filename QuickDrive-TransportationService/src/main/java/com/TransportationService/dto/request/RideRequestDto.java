package com.TransportationService.dto.request;

import com.TransportationService.entity.*;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RideRequestDto {

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
}
