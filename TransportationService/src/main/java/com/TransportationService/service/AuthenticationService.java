package com.TransportationService.service;

import com.TransportationService.dto.request.*;
import com.TransportationService.dto.response.JwtAuthenticationResponse;
import com.TransportationService.entity.Admin;
import com.TransportationService.entity.Customer;
import com.TransportationService.entity.Driver;
import com.TransportationService.entity.Role;

import java.util.Optional;

public interface AuthenticationService {
    Driver signup(DriverDto driverDto);

    Customer signup(CustomerDto customerDto);

    Admin signup(AdminDto adminDto);

    JwtAuthenticationResponse signin(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
