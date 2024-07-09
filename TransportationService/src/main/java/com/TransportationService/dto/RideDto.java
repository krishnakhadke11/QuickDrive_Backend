package com.TransportationService.dto;

import com.TransportationService.entity.Cab;
import com.TransportationService.entity.Customer;
import lombok.Data;

@Data
public class RideDto {
    private String pickupLocation;
    private String dropLocation;
    private int customer_id;
    private int cab_id;
    private int driver_id;
    private String paymentType;
}
