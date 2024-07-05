package com.TransportationService.service.impl;

import com.TransportationService.entity.Cab;
import com.TransportationService.entity.Driver;
import com.TransportationService.entity.Role;
import com.TransportationService.entity.User;
import com.TransportationService.repository.CabRepository;
import com.TransportationService.repository.DriverRepository;
import com.TransportationService.repository.UserRepository;
import com.TransportationService.service.DriverService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

    private DriverRepository driverRepository;
    private CabRepository cabRepository;

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository, CabRepository cabRepository) {
        this.driverRepository = driverRepository;
        this.cabRepository = cabRepository;
    }


    @Override
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    @Override
    public Driver getDriverById(int id) {
        Optional<Driver> driverOptional = driverRepository.findById(id);
        Driver driver = null;
        if (driverOptional.isPresent()) {
            driver = driverOptional.get();
        }else{
            throw new EntityNotFoundException("Driver Not Found");
        }
        return driver;
    }

    @Override
    @Transactional
    public Driver updateDriver(Driver driver) {
        boolean isPresent = driverRepository.existsById(driver.getId());
        Driver updatedDriver = null;
        if(isPresent){
            updatedDriver = driverRepository.save(driver);
        }
        return updatedDriver;
    }

    @Override
    @Transactional
    public void deleteDriver(int id) {
        boolean isPresent = driverRepository.existsById(id);
        if(isPresent){
            driverRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("Driver Not Found For Delete");
        }
    }

    @Override
    public List<Cab> driverOwnedCabs(int driverId) {
        User user = driverRepository.findById(driverId).get().getUser();
        List<Cab> cabs = cabRepository.findCabByUserId(user.getId());
        return cabs;
    }

    @Override
    @Transactional
    public Driver addDriver(Driver driver) {
        try {
            System.out.println("Driver in Service : " + driver);
            return driverRepository.save(driver);
        }catch (Exception e){
            System.out.println("In driver Servicce");
            e.printStackTrace();
            return null;
        }
    }


}
