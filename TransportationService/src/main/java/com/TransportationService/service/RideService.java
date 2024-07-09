package com.TransportationService.service;

import com.TransportationService.dto.RideDto;
import com.TransportationService.entity.Ride;
import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.util.List;

public interface RideService {
    Ride addRide(RideDto rideDto) throws IOException, InterruptedException, ApiException;

    Ride getRideById(int rideId);

    List<Ride> getAllRideByCustomerId(int customerId);

    Ride updateRide(Ride ride);

    void deleteRide(int rideId);
}
