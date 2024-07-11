package com.TransportationService.controller;

import com.TransportationService.entity.Ride;
import com.TransportationService.service.RideService;
import io.swagger.v3.oas.annotations.Operation;
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
    @PostMapping("/ride")
    public ResponseEntity<Ride> addRide(@RequestBody Ride ride) {
        Ride savedRide = rideService.addRide(ride);
        return ResponseEntity.ok(savedRide);
    }

    @Operation(summary = "Get All the rides", description = "Returns the list of all the rides")
    @GetMapping("/ride")
    public ResponseEntity<List<Ride>> getAllRides() {
        List<Ride> rides = rideService.findAllRides();
        return ResponseEntity.ok(rides);
    }

    @Operation(summary = "Get a ride by id", description = "Returns a ride as per the id")
    @GetMapping("/ride/{id}")
    public ResponseEntity<Ride> getRide(@PathVariable int id) {
        Ride ride = rideService.findRideById(id);
        return ResponseEntity.ok(ride);
    }

    @Operation(summary = "Update Ride", description = "Returns the Ride after updating")
    @PutMapping("/ride")
    public ResponseEntity<Ride> updateRide(@RequestBody Ride ride) {
        Ride updatedRide =  rideService.updateRide(ride);
        return ResponseEntity.ok(updatedRide);
    }

    @Operation(summary = "Delete a ride by id", description = "Returns a confirmation string")
    @DeleteMapping("/ride/{id}")
    public ResponseEntity<String> deleteRide(@PathVariable int id) {
        rideService.deleteRide(id);
        return ResponseEntity.ok("Ride has been deleted");
    }
}