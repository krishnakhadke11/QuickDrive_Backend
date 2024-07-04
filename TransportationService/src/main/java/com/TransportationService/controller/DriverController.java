package com.TransportationService.controller;


import com.TransportationService.entity.Driver;
import com.TransportationService.entity.User;
import com.TransportationService.service.DriverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private DriverService driverService;

    @GetMapping("/drivers")
    public ResponseEntity<List<Driver>> getUsers() {
        List<Driver> drivers = driverService.getAllDrivers();
        return ResponseEntity.ok().body(drivers);
    }
}
