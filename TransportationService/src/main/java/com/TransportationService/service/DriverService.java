package com.TransportationService.service;

import com.TransportationService.entity.Cab;
import com.TransportationService.entity.Driver;

import java.util.List;

public interface DriverService {

    Driver addDriver(Driver driver);

    List<Driver> getAllDrivers();

    Driver getDriverById(int id);

    Driver updateDriver(Driver driver);

    void deleteDriver(int id);

    List<Cab> driverOwnedCabs(int driverId);

}
