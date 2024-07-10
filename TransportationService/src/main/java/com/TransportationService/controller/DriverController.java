package com.TransportationService.controller;


import com.TransportationService.entity.Cab;
import com.TransportationService.entity.Driver;
import com.TransportationService.entity.DriverOperation;
import com.TransportationService.service.DriverOperationService;
import com.TransportationService.service.DriverService;
import com.TransportationService.service.UserService;
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

    @PostMapping("/driver")
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) {
        Driver savedDriver = driverService.addDriver(driver);
        System.out.println("Controller driver" + savedDriver.toString());
        return ResponseEntity.ok().body(savedDriver);
    }

    @GetMapping("/driver")
    public ResponseEntity<List<Driver>> getUsers() {
        List<Driver> drivers = driverService.getAllDrivers();
        return ResponseEntity.ok().body(drivers);
    }

    @GetMapping("/driver/{id}")
    public ResponseEntity<Driver> getUserById(@PathVariable int id) {
        Driver driver = driverService.getDriverById(id);
        return ResponseEntity.ok().body(driver);
    }

    @GetMapping("/driver/driveroperations")
    public ResponseEntity<DriverOperation> getDriverOperationById(HttpServletRequest req){
        Integer driverId = (Integer)req.getAttribute("id");
        DriverOperation driverOperation = driverOperationService.getDriverOperationByDriverId(driverId);
        return ResponseEntity.ok().body(driverOperation);
    }

    @PutMapping("/driver")
    public ResponseEntity<Driver> updateDriver(@RequestBody Driver driver) {
        Driver updateDriver = driverService.updateDriver(driver);
        return ResponseEntity.ok().body(updateDriver);
    }

    @DeleteMapping("/driver/{id}")
    public ResponseEntity<String> deleteDriver(@PathVariable int id) {
        driverService.deleteDriver(id);
        return ResponseEntity.ok().body("Driver has been deleted");
    }

    @GetMapping("driver/cabs")
    public ResponseEntity<List<Cab>> getCabs(HttpServletRequest req) {
        Integer driverId = (Integer) req.getAttribute("id");
        List<Cab> cabs = driverService.driverOwnedCabs(driverId);
        return ResponseEntity.ok().body(cabs);
    }
}
