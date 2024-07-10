package com.TransportationService.service.impl;

import com.TransportationService.entity.*;
import com.TransportationService.repository.*;
import com.TransportationService.service.AdminService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, CabRepository cabRepository, DriverRepository driverRepository, DriverOperationRepository driverOperationRepository, PaymentRepository paymentRepository, RideRepository rideRepository) {
        this.adminRepository = adminRepository;
        this.cabRepository = cabRepository;
        this.driverRepository = driverRepository;
        this.driverOperationRepository = driverOperationRepository;
        this.paymentRepository = paymentRepository;
        this.rideRepository = rideRepository;
    }

    @Override
    public Admin addAdmin(Admin admin) {
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
