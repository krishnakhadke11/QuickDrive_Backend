package com.TransportationService.service;

import com.TransportationService.dto.request.AdminDto;
import com.TransportationService.entity.*;

import java.util.List;

public interface AdminService {
    Admin addAdmin(AdminDto adminDto);

    List<Cab> adminOwnedCabs(int adminId);

    List<DriverOperation> findAllDriverOperations();

    List<Payment> findAllPayments();

    List<Ride> findAllRides();

    List<Driver> findAllDrivers();

    boolean adminExists();
}
