package com.TransportationService.dto.request;


import com.TransportationService.entity.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RideDto {

    private String pickupLocation;

    private String dropLocation;

    private int fare;

    private String distance;

    private String duration;

    private CustomerIdDto customer;

    private CabIdDto cab;

    private DriverIdDto driver;
}

