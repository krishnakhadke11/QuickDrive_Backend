package com.TransportationService.controller;


import com.TransportationService.entity.Cab;
import com.TransportationService.entity.Driver;
import com.TransportationService.entity.DriverOperation;
import com.TransportationService.service.DriverOperationService;
import com.TransportationService.service.DriverService;
import com.TransportationService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DriverController {

    private DriverService driverService;
    private DriverOperationService driverOperationService;

    @Autowired
    public DriverController(DriverService driverService, DriverOperationService driverOperationService) {
        this.driverService = driverService;
        this.driverOperationService = driverOperationService;
    }

    @Operation(summary = "Create a driver", description = "Returns the newly created driver")
    @PostMapping("/driver")
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) {
        Driver savedDriver = driverService.addDriver(driver);
        System.out.println("Controller driver" + savedDriver.toString());
        return ResponseEntity.ok().body(savedDriver);
    }

    @Operation(summary = "Get all drivers", description = "Returns the list of drivers")
    @GetMapping("/driver")
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> drivers = driverService.getAllDrivers();
        return ResponseEntity.ok().body(drivers);
    }

    @Operation(summary = "Get a driver by id", description = "Returns the driver as per the id")
    @GetMapping("/driver/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable int id) {
        Driver driver = driverService.getDriverById(id);
        return ResponseEntity.ok().body(driver);
    }

    @Operation(summary = "Get the drivers operations", description = "Returns driveroperation")
    @GetMapping("/driver/driveroperations")
    public ResponseEntity<?> getDriverOperationById(HttpServletRequest req){
        Integer driverId = (Integer)req.getAttribute("id");
        DriverOperation driverOperation = driverOperationService.getDriverOperationByDriverId(driverId);
        System.out.println("DriverOperation Controller : " + driverOperation);
        return ResponseEntity.ok().body(driverOperation==null ? "null":driverOperation);
    }

    @Operation(summary = "Update a driver", description = "Returns the updated driver")
    @PutMapping("/driver")
    public ResponseEntity<Driver> updateDriver(@RequestBody Driver driver) {
        Driver updateDriver = driverService.updateDriver(driver);
        return ResponseEntity.ok().body(updateDriver);
    }

    @Operation(summary = "Delete a driver by id", description = "Returns the confirmation string")
    @DeleteMapping("/driver/{id}")
    public ResponseEntity<String> deleteDriver(@PathVariable int id) {
        driverService.deleteDriver(id);
        return ResponseEntity.ok().body("Driver has been deleted");
    }

    @Operation(summary = "Get all the driver owned cabs", description = "Returns the list of driver owned cabs")
    @GetMapping("driver/cabs")
    public ResponseEntity<List<Cab>> getCabs(HttpServletRequest req) {
        Integer driverId = (Integer) req.getAttribute("id");
        List<Cab> cabs = driverService.driverOwnedCabs(driverId);
        return ResponseEntity.ok().body(cabs);
    }
}
