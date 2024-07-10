package com.TransportationService.controller;

import com.TransportationService.dto.request.SignInRequest;
import com.TransportationService.dto.response.JwtAuthenticationResponse;
import com.TransportationService.entity.Customer;
import com.TransportationService.entity.Driver;
import com.TransportationService.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/auth/driver/signup")
    public ResponseEntity<Driver>signup(@RequestBody Driver driver) {
        Driver savedDriver = authenticationService.signup(driver);
        return ResponseEntity.ok(savedDriver);
    }

    @PostMapping("/auth/customer/signup")
    public ResponseEntity<Customer>signup(@RequestBody Customer customer) {
        Customer savedCustomer = authenticationService.signup(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest){
        JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.signin(signInRequest);
        return ResponseEntity.ok(jwtAuthenticationResponse);
    }
}
