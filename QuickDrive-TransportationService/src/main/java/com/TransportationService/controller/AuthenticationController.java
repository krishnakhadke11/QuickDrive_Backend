package com.TransportationService.controller;

import com.TransportationService.dto.request.*;
import com.TransportationService.dto.response.JwtAuthenticationResponse;
import com.TransportationService.entity.Admin;
import com.TransportationService.entity.Customer;
import com.TransportationService.entity.Driver;
import com.TransportationService.service.AuthenticationService;
import com.TransportationService.validation.CustomerValidation;
import com.TransportationService.validation.DriverValidation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private AuthenticationService authenticationService;
    private DriverValidation driverValidation;
    private CustomerValidation customerValidation;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, DriverValidation driverValidation, CustomerValidation customerValidation) {
        this.authenticationService = authenticationService;
        this.driverValidation = driverValidation;
        this.customerValidation = customerValidation;
    }

    @Operation(summary = "Driver Signup", description = "Returns the Driver Details")
    @PostMapping("/auth/drivers/signup")
    public ResponseEntity<Driver>signup(@RequestBody DriverDto driverDto) {
        System.out.println(driverDto);
        //Validations
        driverValidation.validateDriver(driverDto);

        Driver savedDriver = authenticationService.signup(driverDto);
        return ResponseEntity.ok(savedDriver);
    }

    @Operation(summary = "Customer Signup", description = "Returns the Customer Details")
    @PostMapping("/auth/customers/signup")
    public ResponseEntity<Customer>signup(@RequestBody CustomerDto customerDto) {
        //Validations
        customerValidation.validateCustomer(customerDto);

        Customer savedCustomer = authenticationService.signup(customerDto);
        return ResponseEntity.ok(savedCustomer);
    }

    @Operation(summary = "Admin Signup", description = "Returns the Admin Details")
    @PostMapping("/auth/admins/signup")
    public ResponseEntity<Admin>signup(@RequestBody AdminDto adminDto) {
        Admin savedAdmin = authenticationService.signup(adminDto);
        return ResponseEntity.ok(savedAdmin);
    }

    @Operation(summary = "Signin", description = "Returns the Auth Token and Refresh Token")
    @PostMapping("/auth/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest){
        JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.signin(signInRequest);
        return ResponseEntity.ok(jwtAuthenticationResponse);
    }

    @Operation(summary = "Refresh Token", description = "Returns the updated token")
    @PostMapping("/auth/refresh-token")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(jwtAuthenticationResponse);
    }


}
