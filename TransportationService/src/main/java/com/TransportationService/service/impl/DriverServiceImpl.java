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
        return driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Driver Not Found"));
    }

    @Override
    @Transactional
    public Driver updateDriver(Driver driver) {
        if(!driverRepository.existsById(driver.getId())){
            throw new EntityNotFoundException("Driver Not Found While Updating");
        }
        return driverRepository.save(driver);
    }

    @Override
    @Transactional
    public void deleteDriver(int id) {

        if(!driverRepository.existsById(id)){
            throw  new EntityNotFoundException("Driver Not Found For Delete");
        }
        driverRepository.deleteById(id);
    }

    @Override
    public List<Cab> driverOwnedCabs(int driverId) {
        //This will either send the driver or Throw the exception
        //Option<Driver> is returned by findById
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver Not Found"));
        User user = driver.getUser();
        return cabRepository.findCabByUserId(user.getId());
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
