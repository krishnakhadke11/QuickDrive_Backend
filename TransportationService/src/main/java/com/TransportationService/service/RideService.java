package com.TransportationService.service;

import com.TransportationService.dto.request.RideDto;
import com.TransportationService.dto.request.RideUpdateDto;
import com.TransportationService.entity.Ride;

import java.util.List;

public interface RideService {
    Ride addRide(RideDto rideDto);

    Ride findRideById(int rideId);

    List<Ride> findAllRideByCustomerId(int customerId);

    List<Ride> findAllRides();

    Ride updateRide(RideUpdateDto rideUpdateDto);

    void deleteRide(int rideId);
}
