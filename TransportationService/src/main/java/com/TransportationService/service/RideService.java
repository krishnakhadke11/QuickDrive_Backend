package com.TransportationService.service;

import com.TransportationService.dto.request.RideDto;
import com.TransportationService.dto.request.RideRatingUpdateDto;
import com.TransportationService.dto.request.RideUpdateDto;
import com.TransportationService.dto.response.DriverRatingResponseDto;
import com.TransportationService.entity.Payment;
import com.TransportationService.entity.Ride;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface RideService {
    Ride addRide(RideDto rideDto);

    Ride findRideById(int rideId);

    List<Ride> findAllRideByCustomerId(int customerId);

    List<Ride> findAllRideByDriverId(int driverId);

    Payment findPaymentByRideId(int rideId);

    Ride getLatestRideOfDriver(int driverId);

    List<Ride> findAllRides();

    Ride updateRide(RideUpdateDto rideUpdateDto);

    Ride updateRideRating(int rideId,RideRatingUpdateDto rideRatingUpdateDto) throws BadRequestException;

    void deleteRide(int rideId);

    DriverRatingResponseDto getAverageRatingByDriverId(int driverId);


}
