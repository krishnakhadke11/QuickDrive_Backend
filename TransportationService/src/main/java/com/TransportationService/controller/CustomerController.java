package com.TransportationService.controller;

import com.TransportationService.entity.Customer;
import com.TransportationService.entity.Ride;
import com.TransportationService.service.CustomerService;
import com.TransportationService.service.RideService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Create a customer", description = "Returns the newly added customer")
    @PostMapping("/customer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.addCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @Operation(summary = "Get All Customers", description = "Returns the list of all the customers  ")
    @GetMapping("/customer")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Get a customer by id", description = "Returns the customer as per the id")
    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @Operation(summary = "Get all the rides of a customer", description = "Returns all the rides of a customer")
    @GetMapping("/customer/rides")
    public ResponseEntity<List<Ride>> getRideByCustomerId(HttpServletRequest req) {
        Integer customerId = (Integer)req.getAttribute("id");
        List<Ride> rides = rideService.findAllRideByCustomerId(customerId);
        return ResponseEntity.ok(rides);
    }

    @Operation(summary = "Update a customer", description = "Returns the updated customer")
    @PutMapping("/customer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        Customer updateCustomer = customerService.updateCustomer(customer);
        return ResponseEntity.ok(updateCustomer);
    }

    @Operation(summary = "Delete a customer by id", description = "Returns the confirmation String")
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }
}