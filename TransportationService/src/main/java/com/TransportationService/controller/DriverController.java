package com.TransportationService.controller;


import com.TransportationService.dto.request.DriverDto;
import com.TransportationService.dto.request.DriverUpdateDto;
import com.TransportationService.entity.Cab;
import com.TransportationService.entity.Driver;
import com.TransportationService.entity.DriverOperation;
import com.TransportationService.entity.Ride;
import com.TransportationService.service.DriverOperationService;
import com.TransportationService.service.DriverService;
import com.TransportationService.service.RideService;
import com.TransportationService.service.UserService;
import com.TransportationService.validation.DriverValidation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DriverController {

    private DriverService driverService;
    private DriverOperationService driverOperationService;
    private DriverValidation driverValidation;

    @Autowired
    public DriverController(DriverService driverService, DriverOperationService driverOperationService, DriverValidation driverValidation) {
        this.driverService = driverService;
        this.driverOperationService = driverOperationService;
        this.driverValidation = driverValidation;
    }

    @Operation(summary = "Create a driver", description = "Returns the newly created driver")
    @PostMapping("/driver")
    public ResponseEntity<Driver> createDriver(@Valid @RequestBody DriverDto driverDto) {
        //Validations
        driverValidation.validateDriver(driverDto);

        Driver savedDriver = driverService.addDriver(driverDto);
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

    @Operation(summary = "Get a driver by id which is extracted from token", description = "Returns the driver as per the id")
    @GetMapping("/driver/details")
    public ResponseEntity<Driver> getDriverById(HttpServletRequest req) {
        Integer id = (Integer) req.getAttribute("id");
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

    @Operation(summary = "Get the Latest Ride of a driver", description = "Returns the latest Ride of a driver")
    @GetMapping("/driver/ride/latest")
    public ResponseEntity<Ride> getLatestRideOfDriver(@NotNull HttpServletRequest req) {
        Integer driverId = (Integer) req.getAttribute("id");
        Ride ride = driverService.getLatestRideOfDriver(driverId);
        return ResponseEntity.ok(ride);
    }

    @Operation(summary = "End the ride and update paymentStatus and Cabstatus", description = "Returns the confirmation string")
    @GetMapping("/driver/end/ride/{id}")
    public ResponseEntity<String> endRide(@PathVariable int id,@NotNull HttpServletRequest req) {
        Integer driverId = (Integer) req.getAttribute("id");
        String msg = driverService.endRide(id,driverId);
        return ResponseEntity.ok(msg);
    }

    @Operation(summary = "Update a driver", description = "Returns the updated driver")
    @PutMapping("/driver")
    public ResponseEntity<Driver> updateDriver(@Valid @RequestBody DriverUpdateDto driverUpdateDto) {
        //Validations
        driverValidation.validateDriver(driverUpdateDto);

        Driver updateDriver = driverService.updateDriver(driverUpdateDto);
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
