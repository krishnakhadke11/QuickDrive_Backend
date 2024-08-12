package com.TransportationService.controller;

import com.TransportationService.dto.request.CustomerDto;
import com.TransportationService.dto.request.CustomerUpdateDto;
import com.TransportationService.dto.response.CustomerResponseDto;
import com.TransportationService.entity.Customer;
import com.TransportationService.entity.Ride;
import com.TransportationService.service.CustomerService;
import com.TransportationService.service.RideService;
import com.TransportationService.validation.CustomerValidation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private CustomerService customerService;
    private RideService rideService;
    private CustomerValidation customerValidation;

    @Autowired
    public CustomerController(CustomerService customerService, RideService rideService, CustomerValidation customerValidation) {
        this.customerService = customerService;
        this.rideService = rideService;
        this.customerValidation = customerValidation;
    }

    @Operation(summary = "Create a customer", description = "Returns the newly added customer")
    @PostMapping("/customer")
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody CustomerDto customerDto) {
        //Validations
        customerValidation.validateCustomer(customerDto);

        Customer savedCustomer = customerService.addCustomer(customerDto);
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
    public ResponseEntity<CustomerResponseDto> getCustomer(@PathVariable int id) {
        CustomerResponseDto customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }


    @Operation(summary = "Get a customer by id which is extracted from token", description = "Returns the customer as per the id")
    @GetMapping("/customer/details")
    public ResponseEntity<CustomerResponseDto> getCustomer(HttpServletRequest req) {
        Integer id = (Integer) req.getAttribute("id");
        CustomerResponseDto customer = customerService.getCustomerById(id);
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
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody CustomerUpdateDto customerUpdateDto) {
        //Validations
        customerValidation.validateCustomer(customerUpdateDto);

        Customer updateCustomer = customerService.updateCustomer(customerUpdateDto);
        return ResponseEntity.ok(updateCustomer);
    }

    @Operation(summary = "Delete a customer by id", description = "Returns the confirmation String")
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }
}
