package com.TransportationService.service;

import com.TransportationService.entity.*;

import java.util.List;

public interface AdminService {
    Admin addAdmin(Admin admin);

    List<Cab> adminOwnedCabs(int adminId);

    List<DriverOperation> findAllDriverOperations();

    List<Payment> findAllPayments();

    List<Ride> findAllRides();

    List<Driver> findAllDrivers();
}
