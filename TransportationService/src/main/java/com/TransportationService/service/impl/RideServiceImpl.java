package com.TransportationService.service.impl;

import com.TransportationService.entity.*;
import com.TransportationService.repository.*;
import com.TransportationService.service.RideService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideServiceImpl implements RideService {


    private final DriverRepository driverRepository;


    private CustomerRepository customerRepository;
    private RideRepository rideRepository;
    private CabRepository cabRepository;
    private PaymentRepository paymentRepository;

    @Autowired
    public RideServiceImpl(CustomerRepository customerRepository, RideRepository rideRepository, CabRepository cabRepository, PaymentRepository paymentRepository, DriverRepository driverRepository) {
        this.customerRepository = customerRepository;
        this.rideRepository = rideRepository;
        this.cabRepository = cabRepository;
        this.paymentRepository = paymentRepository;
        this.driverRepository = driverRepository;
    }


    @Override
    @Transactional
    public Ride addRide(Ride ride) {

        Customer customer = customerRepository.findById(ride.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cab cab = cabRepository.findById(ride.getCab().getId()).orElseThrow(() -> new RuntimeException("Cab not found"));

        Driver driver = driverRepository.findById(ride.getDriver().getId()).orElseThrow(() -> new RuntimeException("Driver not found"));

        ride.setCustomer(customer);
        ride.setCab(cab);
        ride.setDriver(driver);

        return rideRepository.save(ride);
    }

    @Override
    public Ride getRideById(int rideId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new RuntimeException("Ride not found"));
        return ride;
    }

    @Override
    public List<Ride> getAllRideByCustomerId(int customerId) {
        if(!customerRepository.existsById(customerId)){
            throw new EntityNotFoundException("Customer not found");
        }
        List<Ride> rides = rideRepository.findRideByCustomerId(customerId);
        return rides;
    }

    @Override
    public Ride updateRide(Ride ride) {
        if(!rideRepository.existsById(ride.getId())){
            throw new EntityNotFoundException("Ride not found");
        }
        return rideRepository.save(ride);
    }

    @Override
    public void deleteRide(int rideId) {
        if(!rideRepository.existsById(rideId)){
            throw new EntityNotFoundException("Ride Not Found");
        }
        rideRepository.deleteById(rideId);
    }
}
