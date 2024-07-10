package com.TransportationService.service.impl;

import com.TransportationService.dto.request.RefreshTokenRequest;
import com.TransportationService.dto.request.SignInRequest;
import com.TransportationService.dto.response.JwtAuthenticationResponse;
import com.TransportationService.entity.Customer;
import com.TransportationService.entity.Driver;
import com.TransportationService.entity.Role;
import com.TransportationService.entity.User;
import com.TransportationService.repository.CustomerRepository;
import com.TransportationService.repository.DriverRepository;
import com.TransportationService.repository.UserRepository;
import com.TransportationService.service.AuthenticationService;
import com.TransportationService.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final DriverRepository driverRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, DriverRepository driverRepository, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.driverRepository = driverRepository;
        this.customerRepository = customerRepository;
    }

    public Driver signup(Driver driver) {
        String password = driver.getUser().getPassword();
        driver.getUser().setPassword(passwordEncoder.encode(password));
        driver.getUser().setRole(Role.DRIVER);
        return driverRepository.save(driver);
    }

    public Customer signup(Customer customer) {
        String password = customer.getUser().getPassword();
        customer.getUser().setPassword(passwordEncoder.encode(password));
        customer.getUser().setRole(Role.CUSTOMER);
        return customerRepository.save(customer);
    }

    public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
        //This will validate the email and password
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() ->
                new IllegalArgumentException("Invalid email or password"));
        Integer id = getRoleId(user);

        var jwt = jwtService.generateToken(user,id);
        var refrestToken = jwtService.generateRefrestToken(new HashMap<>(),user,id);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refrestToken);

        return jwtAuthenticationResponse;
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        Integer id = getRoleId(user);
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(),user)){
            var jwt = jwtService.generateToken(user,id);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }

    public Integer getRoleId(User user){
        Integer id = null;
        if(user.getRole() == Role.DRIVER){
            id = driverRepository.findDriverByUserId(user.getId()).getId();
        }else if(user.getRole() == Role.CUSTOMER){
            id = customerRepository.findCustomerByUserId(user.getId()).getId();
        }
        return id;
    }
}
