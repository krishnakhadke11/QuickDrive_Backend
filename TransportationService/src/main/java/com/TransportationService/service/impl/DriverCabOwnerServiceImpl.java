package com.TransportationService.service.impl;

import com.TransportationService.entity.DriverCabOwner;
import com.TransportationService.repository.DriverCabOwnerRepository;
import com.TransportationService.service.DriverCabOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverCabOwnerServiceImpl implements DriverCabOwnerService {

    @Autowired
    private DriverCabOwnerRepository driverCabOwnerRepository;

    public List<DriverCabOwner> getAllDriverCabOwners(){
        return driverCabOwnerRepository.findAll();
    }

    public DriverCabOwner findDriverCabOwnerById(int id){
        return driverCabOwnerRepository.findById(id).get();
    }

    public DriverCabOwner addDriverCabOwners(DriverCabOwner driverCabOwner){
        return driverCabOwnerRepository.save(driverCabOwner);
    }
}
