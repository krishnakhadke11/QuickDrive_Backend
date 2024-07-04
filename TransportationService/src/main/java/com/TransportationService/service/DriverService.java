package com.TransportationService.service;

import com.TransportationService.entity.Driver;

import java.util.List;

public interface DriverService {

    List<Driver> getAllDrivers();

    public Driver findDriverById(int id);

    public Driver addDriver(Driver driver);
}
