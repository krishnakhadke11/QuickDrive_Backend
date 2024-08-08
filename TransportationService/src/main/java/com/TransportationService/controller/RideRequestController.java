package com.TransportationService.controller;

import com.TransportationService.dto.request.RideRequestDto;
import com.TransportationService.dto.response.RideRequestResponseDto;
import com.TransportationService.dto.response.RideRequestWithRideDto;
import com.TransportationService.entity.Ride;
import com.TransportationService.entity.RideRequest;
import com.TransportationService.service.RideRequestService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RideRequestController {

    private RideRequestService rideRequestService;

    public RideRequestController(RideRequestService rideRequestService) {
        this.rideRequestService = rideRequestService;
    }

    @Operation(summary = "Create Ride Request", description = "Creating the Ride Request")
    @PostMapping("/riderequest")
    public ResponseEntity<RideRequest> createRideRequest(@RequestBody RideRequestDto rideRequest, HttpServletRequest req) {

        Integer customerId = (Integer)req.getAttribute("id");
        RideRequest rideReq = rideRequestService.addRideRequest(rideRequest,customerId);
        return ResponseEntity.ok(rideReq);
    }
    // /accept request and create ride
    @Operation(summary = "Accept Ride Request", description = "Accepting the Ride Request")
    @GetMapping("/riderequest/accept/{id}")
    public Object acceptRideRequest(@PathVariable int id, HttpServletRequest req) {
        //Only Driver can accept the ride :
        Integer driverId = (Integer)req.getAttribute("id");
        Ride ride = rideRequestService.acceptRideRequest(id,driverId);
        if(ride != null){
            return ResponseEntity.ok(ride);
        }else{
            return ResponseEntity.internalServerError();
        }
    }

    @Operation(summary = "Get Ride Request By Id", description = "Getting the Ride Request By Id")
    @GetMapping("/riderequest/{id}")
    public ResponseEntity<RideRequestResponseDto> getRideRequestById(@PathVariable int id){
        RideRequestResponseDto rideRequest = rideRequestService.getRideRequestById(id);
        return  ResponseEntity.ok(rideRequest);
    }

    @Operation(summary = "Get All Ride Request", description = "Getting All Ride Request")
    @GetMapping("/riderequest")
    public ResponseEntity<List<RideRequest>> getAllRideRequest(){
        List<RideRequest> rideRequest = rideRequestService.getAllRideRequest();
        return  ResponseEntity.ok(rideRequest);
    }

    @Operation(summary = "Get All Ride Request", description = "Getting All Ride Request")
    @GetMapping("/riderequest/{id}/ride")
    public ResponseEntity<RideRequestWithRideDto> getRideFromRideReqId(@PathVariable int id){
        RideRequestWithRideDto rideRequest = rideRequestService.findByIdWithRide(id);
        return  ResponseEntity.ok(rideRequest);
    }
}
