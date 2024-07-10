package com.TransportationService.controller;

import com.TransportationService.entity.Customer;
import com.TransportationService.entity.Ride;
import com.TransportationService.service.CustomerService;
import com.TransportationService.service.RideService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private CustomerService customerService;
    private RideService rideService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.addCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @GetMapping("/customer")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/customer/rides")
    public ResponseEntity<List<Ride>> getRideByCustomerId(HttpServletRequest req) {
        Integer customerId = (Integer)req.getAttribute("id");
        List<Ride> rides = rideService.getAllRideByCustomerId(customerId);
        return ResponseEntity.ok(rides);
    }

    @PutMapping("/customer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        Customer updateCustomer = customerService.updateCustomer(customer);
        return ResponseEntity.ok(updateCustomer);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }
}
