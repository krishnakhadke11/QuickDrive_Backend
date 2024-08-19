package com.TransportationService.service.impl;

import com.TransportationService.dto.request.*;
import com.TransportationService.dto.response.JwtAuthenticationResponse;
import com.TransportationService.entity.*;
import com.TransportationService.repository.AdminRepository;
import com.TransportationService.repository.CustomerRepository;
import com.TransportationService.repository.DriverRepository;
import com.TransportationService.repository.UserRepository;
import com.TransportationService.service.AuthenticationService;
import com.TransportationService.service.JwtService;
import com.TransportationService.validation.DriverValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final DriverRepository driverRepository;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, DriverRepository driverRepository, CustomerRepository customerRepository, AdminRepository adminRepository, DriverValidation driverValidation) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.driverRepository = driverRepository;
        this.customerRepository = customerRepository;
        this.adminRepository = adminRepository;
    }


    @Override
    public Driver signup(DriverDto driverDto) {

        String password = driverDto.getUser().getPassword();

        Driver driver = new Driver();
        driver.setDriversLicense(driverDto.getDriversLicense());
//        driver.setStartTime(driverDto.getStartTime());
//        driver.setEndTime(driverDto.getEndTime());

        User user = new User();
        user.setFirstName(driverDto.getUser().getFirstName());
        user.setLastName(driverDto.getUser().getLastName());
        user.setEmail(driverDto.getUser().getEmail());
        user.setPhoneNumber(driverDto.getUser().getPhoneNumber());
        user.setAddress(driverDto.getUser().getAddress());
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.DRIVER);

        driver.setUser(user);

        return driverRepository.save(driver);
    }

    @Override
    public Customer signup(CustomerDto customerDto) {
        String password = customerDto.getUser().getPassword();
        Customer customer = new Customer();

        User user = new User();
        user.setFirstName(customerDto.getUser().getFirstName());
        user.setLastName(customerDto.getUser().getLastName());
        user.setEmail(customerDto.getUser().getEmail());
        user.setPhoneNumber(customerDto.getUser().getPhoneNumber());
        user.setAddress(customerDto.getUser().getAddress());
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.CUSTOMER);

        customer.setUser(user);
        return customerRepository.save(customer);
    }

    @Override
    public Admin signup(AdminDto adminDto) {
        String password = adminDto.getUser().getPassword();
        Admin admin = new Admin();

        User user = new User();
        user.setFirstName(adminDto.getUser().getFirstName());
        user.setLastName(adminDto.getUser().getLastName());
        user.setEmail(adminDto.getUser().getEmail());
        user.setPhoneNumber(adminDto.getUser().getPhoneNumber());
        user.setAddress(adminDto.getUser().getAddress());
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.ADMIN);

        admin.setUser(user);
        return adminRepository.save(admin);
    }

    public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
        //This will validate the email and password
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() ->
                new BadCredentialsException("Invalid email or password"));
        Integer id = getRoleId(user);

        var jwt = jwtService.generateToken(user,id);
        var refrestToken = jwtService.generateRefrestToken(new HashMap<>(),user,id);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refrestToken);
        jwtAuthenticationResponse.setRole(user.getRole());

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
        }else if(user.getRole() == Role.ADMIN){
            id = adminRepository.findAdminByUserId(user.getId()).getId();
        }
        return id;
    }
}
