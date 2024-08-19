package com.TransportationService.service;

import com.TransportationService.dto.request.DriverDto;
import com.TransportationService.dto.request.DriverUpdateDto;
import com.TransportationService.entity.Cab;
import com.TransportationService.entity.Driver;
import com.TransportationService.entity.Ride;

import java.util.List;

public interface DriverService {

    Driver addDriver(DriverDto driverDto);

    List<Driver> getAllDrivers();

    Driver getDriverById(int id);

    Driver updateDriver(DriverUpdateDto driverUpdateDto);

    void deleteDriver(int id);

    List<Cab> driverOwnedCabs(int driverId);

    String endRide(int rideId,int driverId);

}
