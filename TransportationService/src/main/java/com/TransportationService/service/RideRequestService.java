package com.TransportationService.service;

import com.TransportationService.dto.request.RideRequestDto;
import com.TransportationService.dto.response.RideRequestResponseDto;
import com.TransportationService.dto.response.RideRequestWithRideDto;
import com.TransportationService.entity.Ride;
import com.TransportationService.entity.RideRequest;

import java.util.List;

public interface RideRequestService {

    RideRequest addRideRequest(RideRequestDto rideRequest, int customerId);

    List<RideRequest> getAllRideRequest();

    RideRequestResponseDto getRideRequestById(int id);

    List<RideRequest> getAllCustomersRideRequest(int customerId);

    String deleteRideRequest(int rideReqId);

    Ride acceptRideRequest(int rideReqId,int driverId);

    RideRequestWithRideDto findByIdWithRide(int rideReqId);
}
