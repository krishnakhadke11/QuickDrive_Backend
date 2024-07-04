package com.TransportationService.service;

import com.TransportationService.entity.DriverCabOwner;

import java.util.List;

public interface DriverCabOwnerService {
    public List<DriverCabOwner> getAllDriverCabOwners();

    public DriverCabOwner findDriverCabOwnerById(int id);

    public DriverCabOwner addDriverCabOwners(DriverCabOwner driverCabOwner);
}
