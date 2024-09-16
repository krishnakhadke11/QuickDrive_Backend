package com.TransportationService.controller;

import com.TransportationService.dto.request.CustomerDto;
import com.TransportationService.dto.request.CustomerUpdateDto;
import com.TransportationService.dto.response.CustomerResponseDto;
import com.TransportationService.entity.Customer;
import com.TransportationService.entity.Payment;
import com.TransportationService.entity.Ride;
import com.TransportationService.service.CustomerService;
import com.TransportationService.service.PaymentService;
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
    private PaymentService paymentService;

    @Autowired
    public CustomerController(CustomerService customerService, RideService rideService, CustomerValidation customerValidation, PaymentService paymentService) {
        this.customerService = customerService;
        this.rideService = rideService;
        this.customerValidation = customerValidation;
        this.paymentService = paymentService;
    }

    @Operation(summary = "Create a customer", description = "Returns the newly added customer")
    @PostMapping("/customers")
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody CustomerDto customerDto) {
        //Validations
        customerValidation.validateCustomer(customerDto);

        Customer savedCustomer = customerService.addCustomer(customerDto);
        return ResponseEntity.ok(savedCustomer);
    }

    @Operation(summary = "Get All Customers", description = "Returns the list of all the customers  ")
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Get a customer by id", description = "Returns the customer as per the id")
    @GetMapping("/customers/{customer-id}")
    public ResponseEntity<CustomerResponseDto> getCustomer(@PathVariable("customer-id") int customerId) {
        CustomerResponseDto customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }


    @Operation(summary = "Get a customer by id which is extracted from token", description = "Returns the customer as per the id")
    @GetMapping("/customers/details")
    public ResponseEntity<CustomerResponseDto> getCustomer(HttpServletRequest req) {
        Integer id = (Integer) req.getAttribute("id");
        CustomerResponseDto customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @Operation(summary = "Get all the rides of a customer", description = "Returns all the rides of a customer")
    @GetMapping("/customers/rides")
    public ResponseEntity<List<Ride>> getRideByCustomerId(HttpServletRequest req) {
        Integer customerId = (Integer)req.getAttribute("id");
        List<Ride> rides = rideService.findAllRideByCustomerId(customerId);
        return ResponseEntity.ok(rides);
    }

    @Operation(summary = "Update a customer", description = "Returns the updated customer")
    @PutMapping("/customers")
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody CustomerUpdateDto customerUpdateDto) {
        //Validations
        customerValidation.validateCustomer(customerUpdateDto);

        Customer updateCustomer = customerService.updateCustomer(customerUpdateDto);
        return ResponseEntity.ok(updateCustomer);
    }

    @Operation(summary = "Delete a customer by id", description = "Returns the confirmation String")
    @DeleteMapping("/customers/{customer-id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("customer-id") int customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    @Operation(summary = "Get all the payments of a customer", description = "Returns the list of payments of a customer")
    @GetMapping("/customers/payments")
    public ResponseEntity<List<Payment>> getAllPaymentsByCustomerId(HttpServletRequest req) {
        Integer id = (Integer) req.getAttribute("id");
        List<Payment> payments = paymentService.findPaymentsByCustomerId(id);
        return ResponseEntity.ok(payments);
    }
}
