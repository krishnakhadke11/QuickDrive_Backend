package com.TransportationService.service.impl;

import com.TransportationService.dto.request.DriverDto;
import com.TransportationService.dto.request.DriverUpdateDto;
import com.TransportationService.entity.Cab;
import com.TransportationService.entity.Driver;
import com.TransportationService.entity.Role;
import com.TransportationService.entity.User;
import com.TransportationService.repository.CabRepository;
import com.TransportationService.repository.DriverRepository;
import com.TransportationService.service.DriverService;
import com.TransportationService.validation.DriverValidation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private DriverRepository driverRepository;
    private CabRepository cabRepository;
    private  PasswordEncoder passwordEncoder;
    private DriverValidation driverValidation;
    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository, CabRepository cabRepository, PasswordEncoder passwordEncoder, DriverValidation driverValidation) {
        this.driverRepository = driverRepository;
        this.cabRepository = cabRepository;
        this.passwordEncoder = passwordEncoder;
        this.driverValidation = driverValidation;
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
    public Driver updateDriver(DriverUpdateDto driverUpdateDto) {
        if(!driverRepository.existsById(driverUpdateDto.getId())){
            throw new EntityNotFoundException("Driver Not Found While Updating");
        }

        driverValidation.validateDriver(driverUpdateDto);

        Driver driver = driverRepository.findById(driverUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));

        Driver updateDriver = new Driver();
        updateDriver.setId(driverUpdateDto.getId());
        updateDriver.setDriversLicense(driverUpdateDto.getDriversLicense());
        updateDriver.setStartTime(driverUpdateDto.getStartTime());
        updateDriver.setEndTime(driverUpdateDto.getEndTime());

        User user = new User();
        user.setId(driverUpdateDto.getUser().getId());
        user.setFirstName(driverUpdateDto.getUser().getFirstName());
        user.setLastName(driverUpdateDto.getUser().getLastName());
        user.setEmail(driverUpdateDto.getUser().getEmail());
        user.setPhoneNumber(driverUpdateDto.getUser().getPhoneNumber());
        user.setAddress(driverUpdateDto.getUser().getAddress());
        user.setRole(Role.DRIVER);
        if(!passwordEncoder.matches(driverUpdateDto.getUser().getPassword(),driver.getUser().getPassword())){
            user.setPassword(passwordEncoder.encode(driverUpdateDto.getUser().getPassword()));
        }

        updateDriver.setUser(user);
        return driverRepository.save(updateDriver);
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
        return cabRepository.findCabsByUserId(user.getId());
    }

    @Override
    @Transactional
    public Driver addDriver(DriverDto driverDto) {
        driverValidation.validateDriver(driverDto);

        String password = driverDto.getUser().getPassword();
        Driver driver = new Driver();
        driver.setDriversLicense(driverDto.getDriversLicense());
        driver.setStartTime(driverDto.getStartTime());
        driver.setEndTime(driverDto.getEndTime());

        User user = new User();
        user.setFirstName(driverDto.getUser().getFirstName());
        user.setLastName(driverDto.getUser().getLastName());
        user.setEmail(driverDto.getUser().getEmail());
        user.setPhoneNumber(driverDto.getUser().getPhoneNumber());
        user.setAddress(driverDto.getUser().getAddress());
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.DRIVER);

        driver.setUser(user);
        return driverRepository.save(driver);
    }


}
