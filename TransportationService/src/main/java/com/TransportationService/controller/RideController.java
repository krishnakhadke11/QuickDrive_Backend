package com.TransportationService.controller;

import com.TransportationService.entity.Ride;
import com.TransportationService.service.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RideController {

    private RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/ride")
    public ResponseEntity<Ride> addRide(@RequestBody Ride ride) {
        Ride savedRide = rideService.addRide(ride);
        return ResponseEntity.ok(savedRide);
    }

    @GetMapping("/ride/{id}")
    public ResponseEntity<Ride> getRide(@PathVariable int id) {
        Ride ride = rideService.getRideById(id);
        return ResponseEntity.ok(ride);
    }

    @PutMapping("/ride")
    public ResponseEntity<Ride> updateRide(@RequestBody Ride ride) {
        Ride updatedRide =  rideService.updateRide(ride);
        return ResponseEntity.ok(updatedRide);
    }

    @DeleteMapping("/ride/{id}")
    public ResponseEntity<String> deleteRide(@PathVariable int rideId) {
        rideService.deleteRide(rideId);
        return ResponseEntity.ok("Ride has been deleted");
    }
}
