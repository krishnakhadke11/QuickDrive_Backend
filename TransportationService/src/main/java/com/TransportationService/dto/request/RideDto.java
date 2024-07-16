package com.TransportationService.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RideDto {

    private String pickupLocation;

    private String dropLocation;

    private int fare;

    @Pattern(regexp = "^\\d+(\\.\\d+)?\\s*Km$")
    private String distance;

    private String duration;

    private CustomerIdDto customer;

    private CabIdDto cab;

    private DriverIdDto driver;
}

