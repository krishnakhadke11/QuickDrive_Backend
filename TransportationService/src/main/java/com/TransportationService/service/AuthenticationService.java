package com.TransportationService.service;

import com.TransportationService.dto.request.RefreshTokenRequest;
import com.TransportationService.dto.request.SignInRequest;
import com.TransportationService.dto.response.JwtAuthenticationResponse;
import com.TransportationService.entity.Customer;
import com.TransportationService.entity.Driver;
import com.TransportationService.entity.User;

public interface AuthenticationService {
    Driver signup(Driver driver);

    Customer signup(Customer customer);

    JwtAuthenticationResponse signin(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
