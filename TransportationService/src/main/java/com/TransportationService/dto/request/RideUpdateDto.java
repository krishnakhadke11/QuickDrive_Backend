package com.TransportationService.dto.request;

import com.TransportationService.entity.PaymentType;
import lombok.Data;

@Data
public class RideUpdateDto {
    private int id;

    private String pickupLocation;

    private String dropLocation;

    private int fare;

    private String distance;

    private String duration;

    private PaymentType paymentType;

    private CustomerIdDto customer;

    private CabIdDto cab;

    private DriverIdDto driver;
}
