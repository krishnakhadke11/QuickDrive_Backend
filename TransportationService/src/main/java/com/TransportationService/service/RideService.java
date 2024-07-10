package com.TransportationService.service;

import com.TransportationService.entity.Ride;

import java.util.List;

public interface RideService {
    Ride addRide(Ride ride);

    Ride getRideById(int rideId);

    List<Ride> getAllRideByCustomerId(int customerId);

    Ride updateRide(Ride ride);

    void deleteRide(int rideId);
}
