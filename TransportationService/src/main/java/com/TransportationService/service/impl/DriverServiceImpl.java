package com.TransportationService.service.impl;

import com.TransportationService.dto.request.DriverDto;
import com.TransportationService.dto.request.DriverUpdateDto;
import com.TransportationService.entity.*;
import com.TransportationService.repository.*;
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
    private RideRepository rideRepository;
    private CabRepository cabRepository;
    private PasswordEncoder passwordEncoder;
    private PaymentRepository paymentRepository;
    private DriverOperationRepository driverOperationRepository;

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository, CabRepository cabRepository, PasswordEncoder passwordEncoder, DriverValidation driverValidation, RideRepository rideRepository, PaymentRepository paymentRepository, DriverOperationRepository driverOperationRepository) {
        this.driverRepository = driverRepository;
        this.cabRepository = cabRepository;
        this.passwordEncoder = passwordEncoder;
        this.rideRepository = rideRepository;
        this.paymentRepository = paymentRepository;
        this.driverOperationRepository = driverOperationRepository;
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

        Driver driver = driverRepository.findById(driverUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));

        Driver updateDriver = new Driver();
        updateDriver.setId(driverUpdateDto.getId());
        updateDriver.setDriversLicense(driverUpdateDto.getDriversLicense());

        User user = new User();
        user.setId(driverUpdateDto.getUser().getId());
        user.setFirstName(driverUpdateDto.getUser().getFirstName());
        user.setLastName(driverUpdateDto.getUser().getLastName());
        user.setEmail(driverUpdateDto.getUser().getEmail());
        user.setPhoneNumber(driverUpdateDto.getUser().getPhoneNumber());
        user.setAddress(driverUpdateDto.getUser().getAddress());
        user.setCreatedAt(driver.getUser().getCreatedAt());
        user.setRole(Role.DRIVER);
        if(driverUpdateDto.getUser().getPassword() != null && !passwordEncoder.matches(driverUpdateDto.getUser().getPassword(),driver.getUser().getPassword())){
            user.setPassword(passwordEncoder.encode(driverUpdateDto.getUser().getPassword()));
        }else{
            user.setPassword(driver.getUser().getPassword());
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

        String password = driverDto.getUser().getPassword();
        Driver driver = new Driver();
        driver.setDriversLicense(driverDto.getDriversLicense());

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


    @Override
    @Transactional
    public String endRide(int rideId,int driverId) {
        Payment payment = paymentRepository.findByRideId(rideId);
        if(payment == null){
            return "Ride Payment Not initiated";
        }
        payment.setPaymentStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);

        DriverOperation driverOperation = driverOperationRepository.findDriverOperationByDriverId(driverId);
        driverOperation.setStatus(CabStatus.AVAILABLE);
        driverOperationRepository.save(driverOperation);

        return "Ride Ended Successfully";
    }
}
