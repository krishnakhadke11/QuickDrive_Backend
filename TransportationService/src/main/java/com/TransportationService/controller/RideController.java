package com.TransportationService.controller;

import com.TransportationService.dto.RideDto;
import com.TransportationService.entity.Ride;
import com.TransportationService.service.RideService;
import com.google.maps.errors.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class RideController {

    private RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/ride")
    public ResponseEntity<Ride> addRide(@RequestBody RideDto rideDto) throws IOException, InterruptedException, ApiException {
        Ride savedRide = rideService.addRide(rideDto);
        return ResponseEntity.ok(savedRide);
    }

    @GetMapping("/ride/{id}")
    public ResponseEntity<Ride> getRide(@PathVariable int id) {
        Ride ride = rideService.getRideById(id);
        return ResponseEntity.ok(ride);
    }

    @GetMapping("/ride/customer/{id}")
    public ResponseEntity<List<Ride>> getRideByCustomerId(@PathVariable int id) {
        List<Ride> rides = rideService.getAllRideByCustomerId(id);
        return ResponseEntity.ok(rides);
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
