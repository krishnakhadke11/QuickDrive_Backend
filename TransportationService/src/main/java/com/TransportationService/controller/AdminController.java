package com.TransportationService.controller;

import com.TransportationService.entity.*;
import com.TransportationService.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

//    @Operation(summary = "Get a product by id", description = "Returns a product as per the id")
    @PostMapping("/admin")
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = adminService.addAdmin(admin);
        return ResponseEntity.ok(savedAdmin);
    }

    @Operation(summary = "Get All the admin owned cabs", description = "Returns a list of cabs owned by admin")
    @GetMapping("/admin/cabs")
    public ResponseEntity<List<Cab>> getAdminOwnedCabs(HttpServletRequest req) {
        Integer id = (Integer)req.getAttribute("id");
        List<Cab> cabs = adminService.adminOwnedCabs(id);
        return ResponseEntity.ok(cabs);
    }

    @Operation(summary = "Get all the driveroperations", description = "Returns the list of driveroperations")
    @GetMapping("/admin/driveroperation")
    public ResponseEntity<List<DriverOperation>> getAdminDriverOperation() {
        List<DriverOperation> driverOperations = adminService.findAllDriverOperations();
        return ResponseEntity.ok(driverOperations);
    }

    @Operation(summary = "Get all the payments", description = "Returns the list of all the payments")
    @GetMapping("/admin/payments")
    public ResponseEntity<List<Payment>> getAllPayments(){
        List<Payment> payments = adminService.findAllPayments();
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Get All the rides", description = "Returns the list of all the ride")
    @GetMapping("/admin/rides")
    public ResponseEntity<List<Ride>> getAllRides(){
        List<Ride> rides = adminService.findAllRides();
        return ResponseEntity.ok(rides);
    }

    @Operation(summary = "Get all the drivers", description = "Returns the list of all the drivers")
    @GetMapping("/admin/drivers")
    public ResponseEntity<List<Driver>> getAllDrivers(){
        List<Driver> drivers = adminService.findAllDrivers();
        return ResponseEntity.ok(drivers);
    }

}
