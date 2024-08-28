package com.TransportationService.dto.request;

import com.TransportationService.entity.BookingStatus;
import com.TransportationService.entity.PaymentType;
import com.TransportationService.entity.SeatingCapacity;
import lombok.Data;

import java.util.Date;

@Data
public class RideRequestUpdateDto {

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

    private RideIdDto ride;
}
