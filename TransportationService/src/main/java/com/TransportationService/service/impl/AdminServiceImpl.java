package com.TransportationService.service.impl;

import com.TransportationService.dto.request.AdminDto;
import com.TransportationService.entity.*;
import com.TransportationService.repository.*;
import com.TransportationService.service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final CabRepository cabRepository;
    private final DriverRepository driverRepository;
    private final DriverOperationRepository driverOperationRepository;
    private final PaymentRepository paymentRepository;
    private final RideRepository rideRepository;
    private AdminRepository adminRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, CabRepository cabRepository, DriverRepository driverRepository, DriverOperationRepository driverOperationRepository, PaymentRepository paymentRepository, RideRepository rideRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.cabRepository = cabRepository;
        this.driverRepository = driverRepository;
        this.driverOperationRepository = driverOperationRepository;
        this.paymentRepository = paymentRepository;
        this.rideRepository = rideRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Admin addAdmin(AdminDto adminDto) {
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

    @Override
    public List<Cab> adminOwnedCabs(int adminId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new EntityNotFoundException("Admin Not Found"));
        return cabRepository.findCabsByUserId(admin.getUser().getId());
    }

    @Override
    public List<DriverOperation> findAllDriverOperations() {
        return driverOperationRepository.findAll();

    }

    @Override
    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public List<Ride> findAllRides() {
        return rideRepository.findAll();
    }

    @Override
    public List<Driver> findAllDrivers() {
        return driverRepository.findAll();
    }


}
