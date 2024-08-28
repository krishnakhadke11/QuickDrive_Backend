package com.TransportationService.service.impl;

import com.TransportationService.dto.request.RideDto;
import com.TransportationService.dto.request.RideRatingUpdateDto;
import com.TransportationService.dto.request.RideUpdateDto;
import com.TransportationService.entity.*;
import com.TransportationService.repository.*;
import com.TransportationService.service.RideService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
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
    public Ride addRide(RideDto rideDto) {

        Customer customer = customerRepository.findById(rideDto.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cab cab = cabRepository.findById(rideDto.getCab().getId()).orElseThrow(() -> new RuntimeException("Cab not found"));

        Driver driver = driverRepository.findById(rideDto.getDriver().getId()).orElseThrow(() -> new RuntimeException("Driver not found"));

        Ride ride = new Ride();
        ride.setPickupLocation(rideDto.getPickupLocation());
        ride.setPickupName(rideDto.getPickupName());

        ride.setDropLocation(rideDto.getDropLocation());
        ride.setDropName(rideDto.getDropName());
        ride.setFare(rideDto.getFare());
        ride.setDistance(rideDto.getDistance());
        ride.setDuration(rideDto.getDuration());
        ride.setPaymentType(rideDto.getPaymentType());
        ride.setCustomer(customer);
        ride.setCab(cab);
        ride.setDriver(driver);

        return rideRepository.save(ride);
    }

    @Override
    public Ride findRideById(int rideId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new RuntimeException("Ride not found"));
        return ride;
    }

    @Override
    public List<Ride> findAllRideByCustomerId(int customerId) {
        if(!customerRepository.existsById(customerId)){
            throw new EntityNotFoundException("Customer not found");
        }
        List<Ride> rides = rideRepository.findByCustomerId(customerId);
        return rides;
    }

    @Override
    public List<Ride> findAllRideByDriverId(int driverId) {
        if(!driverRepository.existsById(driverId)){
            throw new EntityNotFoundException("Driver not found");
        }
        List<Ride> rides = rideRepository.findByDriverId(driverId);
        return rides;
    }

    @Override
    public Payment findPaymentByRideId(int rideId) {
        return paymentRepository.findByRideId(rideId);
    }

    @Override
    public Ride getLatestRideOfDriver(int driverId) {
        return rideRepository.findTopByDriverIdOrderByCreatedAtDesc(driverId);
    }

    @Override
    public List<Ride> findAllRides() {
        return rideRepository.findAll();
    }

    @Override
    public Ride updateRide(RideUpdateDto rideUpdateDto) {

        if(!rideRepository.existsById(rideUpdateDto.getId())){
            throw new EntityNotFoundException("Ride not found");
        }

        Customer customer = customerRepository.findById(rideUpdateDto.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cab cab = cabRepository.findById(rideUpdateDto.getCab().getId()).orElseThrow(() -> new RuntimeException("Cab not found"));

        Driver driver = driverRepository.findById(rideUpdateDto.getDriver().getId()).orElseThrow(() -> new RuntimeException("Driver not found"));

        Ride updatedRide = new Ride();
        updatedRide.setId(rideUpdateDto.getId());
        updatedRide.setPickupLocation(rideUpdateDto.getPickupLocation());
        updatedRide.setPickupName(rideUpdateDto.getPickupName());
        updatedRide.setDropLocation(rideUpdateDto.getDropLocation());
        updatedRide.setDropName(rideUpdateDto.getDropName());
        updatedRide.setFare(rideUpdateDto.getFare());
        updatedRide.setDistance(rideUpdateDto.getDistance());
        updatedRide.setDuration(rideUpdateDto.getDuration());
        updatedRide.setPaymentType(rideUpdateDto.getPaymentType());
        updatedRide.setCustomer(customer);
        updatedRide.setCab(cab);
        updatedRide.setDriver(driver);
        return rideRepository.save(updatedRide);
    }

    @Override
    public Ride updateRideRating(int rideId, RideRatingUpdateDto rideRatingUpdateDto) throws BadRequestException {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if(ride.getRating() != null){
            throw new BadRequestException("Rating has already been provided");
        }else{
            ride.setRating(rideRatingUpdateDto.getRating());
            return rideRepository.save(ride);
        }
    }

    @Override
    public void deleteRide(int rideId) {
        if(!rideRepository.existsById(rideId)){
            throw new EntityNotFoundException("Ride Not Found");
        }
        rideRepository.deleteById(rideId);
    }


}
