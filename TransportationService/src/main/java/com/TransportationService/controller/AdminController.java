package com.TransportationService.controller;

import com.TransportationService.entity.*;
import com.TransportationService.service.AdminService;
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

    @PostMapping("/admin")
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = adminService.addAdmin(admin);
        return ResponseEntity.ok(savedAdmin);
    }

    @GetMapping("/admin/{id}/cabs")
    public ResponseEntity<List<Cab>> getAdminOwnedCabs(@PathVariable int id) {
        List<Cab> cabs = adminService.adminOwnedCabs(id);
        return ResponseEntity.ok(cabs);
    }

    @GetMapping("/admin/driveroperation")
    public ResponseEntity<List<DriverOperation>> getAdminDriverOperation() {
        List<DriverOperation> driverOperations = adminService.findAllDriverOperations();
        return ResponseEntity.ok(driverOperations);
    }

    @GetMapping("/admin/payments")
    public ResponseEntity<List<Payment>> getAllPayments(){
        List<Payment> payments = adminService.findAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/admin/rides")
    public ResponseEntity<List<Ride>> getAllRides(){
        List<Ride> rides = adminService.findAllRides();
        return ResponseEntity.ok(rides);
    }

    @GetMapping("/admin/drivers")
    public ResponseEntity<List<Driver>> getAllDrivers(){
        List<Driver> drivers = adminService.findAllDrivers();
        return ResponseEntity.ok(drivers);
    }

}
