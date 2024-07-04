package com.TransportationService.service.impl;

import com.TransportationService.entity.Driver;
import com.TransportationService.repository.DriverRepository;
import com.TransportationService.service.DriverService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver findDriverById(int id) {
        return driverRepository.findById(id).get();
    }

    @Transactional
    public Driver addDriver(Driver driver) {
        return driverRepository.save(driver);
    }
}
