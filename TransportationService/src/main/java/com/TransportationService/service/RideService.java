package com.TransportationService.service;

import com.TransportationService.entity.Ride;

import java.util.List;

public interface RideService {
    Ride addRide(Ride ride);

    Ride findRideById(int rideId);

    List<Ride> findAllRideByCustomerId(int customerId);

    List<Ride> findAllRides();

    Ride updateRide(Ride ride);

    void deleteRide(int rideId);
}
