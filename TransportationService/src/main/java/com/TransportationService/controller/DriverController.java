package com.TransportationService.controller;

import com.TransportationService.dto.request.DriverDto;
import com.TransportationService.dto.request.DriverUpdateDto;
import com.TransportationService.dto.response.EarningResponse;
import com.TransportationService.entity.*;
import com.TransportationService.service.*;
import com.TransportationService.validation.DriverValidation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class DriverController {

    private DriverService driverService;
    private RideService rideService;
    private DriverOperationService driverOperationService;
    private PaymentService paymentService;
    private DriverValidation driverValidation;

    @Autowired
    public DriverController(DriverService driverService, RideService rideService, DriverOperationService driverOperationService, PaymentService paymentService, DriverValidation driverValidation) {
        this.driverService = driverService;
        this.rideService = rideService;
        this.driverOperationService = driverOperationService;
        this.paymentService = paymentService;
        this.driverValidation = driverValidation;
    }

    @Operation(summary = "Create a driver", description = "Returns the newly created driver")
    @PostMapping("/drivers")
    public ResponseEntity<Driver> createDriver(@Valid @RequestBody DriverDto driverDto) {
        //Validations
        driverValidation.validateDriver(driverDto);

        Driver savedDriver = driverService.addDriver(driverDto);
        System.out.println("Controller driver" + savedDriver.toString());
        return ResponseEntity.ok().body(savedDriver);
    }

    @Operation(summary = "Get all drivers", description = "Returns the list of drivers")
    @GetMapping("/drivers")
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> drivers = driverService.getAllDrivers();
        return ResponseEntity.ok().body(drivers);
    }

    @Operation(summary = "Get a driver by id", description = "Returns the driver as per the id")
    @GetMapping("/drivers/{driver-id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable("driver-id") int driverId) {
        Driver driver = driverService.getDriverById(driverId);
        return ResponseEntity.ok().body(driver);
    }

    @Operation(summary = "Get a driver by id which is extracted from token", description = "Returns the driver as per the id")
    @GetMapping("/drivers/details")
    public ResponseEntity<Driver> getDriverById(HttpServletRequest req) {
        Integer id = (Integer) req.getAttribute("id");
        Driver driver = driverService.getDriverById(id);
        return ResponseEntity.ok().body(driver);
    }

    @Operation(summary = "Get the drivers operations", description = "Returns driveroperation")
    @GetMapping("/drivers/driveroperations")
    public ResponseEntity<DriverOperation> getDriverOperationById(HttpServletRequest req){
        Integer driverId = (Integer)req.getAttribute("id");
        DriverOperation driverOperation = driverOperationService.getDriverOperationByDriverId(driverId);
        return ResponseEntity.ok().body(driverOperation);
    }


    @Operation(summary = "Get All the Drivers Ride", description = "Returns the driver Ride")
    @GetMapping("/drivers/rides")
    public ResponseEntity<List<Ride>> getAllDriverRide(
            @RequestParam(value = "sort", required = false) String sortField,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "limit", required = false) Integer limit,
            @NotNull HttpServletRequest req) {
        Integer driverId = (Integer) req.getAttribute("id");

        if (sortField != null && limit != null && order != null && sortField.equals("createdAt")
                && order.equals("desc") && limit == 1) {
            Ride ride = rideService.getLatestRideOfDriver(driverId);
            return ResponseEntity.ok(Collections.singletonList(ride));
        } else {
            List<Ride> rides = rideService.findAllRideByDriverId(driverId);
            return ResponseEntity.ok(rides);
        }
    }

//    @Operation(summary = "End the ride and update paymentStatus and Cabstatus", description = "Returns the confirmation string")
//    @GetMapping("/driver/end/ride/{id}")
//    public ResponseEntity<String> endRide(@PathVariable int id,@NotNull HttpServletRequest req) {
//        Integer driverId = (Integer) req.getAttribute("id");
//        String msg = driverService.endRide(id,driverId);
//        return ResponseEntity.ok(msg);
//    }

    @Operation(summary = "Update a driver", description = "Returns the updated driver")
    @PutMapping("/drivers")
    public ResponseEntity<Driver> updateDriver(@Valid @RequestBody DriverUpdateDto driverUpdateDto) {
        //Validations
        driverValidation.validateDriver(driverUpdateDto);

        Driver updateDriver = driverService.updateDriver(driverUpdateDto);
        return ResponseEntity.ok().body(updateDriver);
    }

    @Operation(summary = "Delete a driver by id", description = "Returns the confirmation string")
    @DeleteMapping("/drivers/{driver-id}")
    public ResponseEntity<String> deleteDriver(@PathVariable("driver-id") int driverId) {
        driverService.deleteDriver(driverId);
        return ResponseEntity.ok().body("Driver has been deleted");
    }

    @Operation(summary = "Get all the driver owned cabs", description = "Returns the list of driver owned cabs")
    @GetMapping("drivers/cabs")
    public ResponseEntity<List<Cab>> getCabs(HttpServletRequest req) {
        Integer driverId = (Integer) req.getAttribute("id");
        List<Cab> cabs = driverService.driverOwnedCabs(driverId);
        return ResponseEntity.ok().body(cabs);
    }

    @Operation(summary = "Get the monthly earnings of a driver", description = "Returns monthly earnings of a driver")
    @GetMapping("/drivers/monthly-earnings")
    public ResponseEntity<EarningResponse> getMonthlyEarnings(HttpServletRequest req) {
        Integer driverId = (Integer) req.getAttribute("id");
        EarningResponse earningResponse = paymentService.getMonthlyEarnings(driverId);
        return ResponseEntity.ok(earningResponse);
    }

    @Operation(summary = "Get All Ride Request As per the Drivers Operational Details", description = "Getting All Ride Request")
    @GetMapping("/drivers/riderequests")
    public ResponseEntity<List<RideRequest>> getAllRideRequestByDriverOps(@org.jetbrains.annotations.NotNull HttpServletRequest req){
        Integer driverId = (Integer)req.getAttribute("id");
        List<RideRequest> rideRequest = driverService.getAllRideReqAsPerDriverOps(driverId);
        return  ResponseEntity.ok(rideRequest);
    }
}
