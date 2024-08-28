package com.TransportationService.controller;

import com.TransportationService.dto.request.RideDto;
import com.TransportationService.dto.request.RideRatingUpdateDto;
import com.TransportationService.dto.request.RideUpdateDto;
import com.TransportationService.entity.Payment;
import com.TransportationService.entity.Ride;
import com.TransportationService.service.RideService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RideController {

    private RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @Operation(summary = "Create a new ride", description = "Returns newly created ride")
    @PostMapping("/rides")
    public ResponseEntity<Ride> addRide(@Valid @RequestBody RideDto rideDto) {
        Ride savedRide = rideService.addRide(rideDto);
        return ResponseEntity.ok(savedRide);
    }

    @Operation(summary = "Get All the rides", description = "Returns the list of all the rides")
    @GetMapping("/rides")
    public ResponseEntity<List<Ride>> getAllRides() {
        List<Ride> rides = rideService.findAllRides();
        return ResponseEntity.ok(rides);
    }

    @Operation(summary = "Get a ride by id", description = "Returns a ride as per the id")
    @GetMapping("/rides/{ride-id}")
    public ResponseEntity<Ride> getRide(@PathVariable("ride-id") int rideId) {
        Ride ride = rideService.findRideById(rideId);
        return ResponseEntity.ok(ride);
    }

    @Operation(summary = "Get Payment by Ride ID", description = "Returns Payment by ride id")
    @GetMapping("/rides/{ride-id}/payments")
    public ResponseEntity<Payment> getPaymentsByRideId(@PathVariable("ride-id") int rideId) {
        Payment payment = rideService.findPaymentByRideId(rideId);
        return ResponseEntity.ok(payment);
    }



    @Operation(summary = "Update Ride", description = "Returns the Ride after updating")
    @PutMapping("/rides")
    public ResponseEntity<Ride> updateRide(@Valid @RequestBody RideUpdateDto rideUpdateDto) {
        Ride updatedRide =  rideService.updateRide(rideUpdateDto);
        return ResponseEntity.ok(updatedRide);
    }

    @Operation(summary = "Update Ride Rating", description = "Returns the Ride after Rating Update")
    @PatchMapping("/rides/{ride-id}/rating")
    public ResponseEntity<Ride> updateRideRating( @PathVariable("ride-id") int rideId,@RequestBody RideRatingUpdateDto rideRatingUpdateDto) throws BadRequestException {
        Ride updatedRide =  rideService.updateRideRating(rideId,rideRatingUpdateDto);
        return ResponseEntity.ok(updatedRide);
    }

    @Operation(summary = "Delete a ride by id", description = "Returns a confirmation string")
    @DeleteMapping("/rides/{ride-id}")
    public ResponseEntity<String> deleteRide(@PathVariable("ride-id") int rideId) {
        rideService.deleteRide(rideId);
        return ResponseEntity.ok("Ride has been deleted");
    }


}
