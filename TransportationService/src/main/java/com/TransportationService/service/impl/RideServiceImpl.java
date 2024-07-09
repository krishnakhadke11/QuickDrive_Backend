package com.TransportationService.service.impl;

import com.TransportationService.dto.RideDto;
import com.TransportationService.entity.*;
import com.TransportationService.repository.*;
import com.TransportationService.service.RideService;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.rmi.UnexpectedException;
import java.util.List;

@Service
public class RideServiceImpl implements RideService {


    private final DriverRepository driverRepository;
    @Value("${google.maps.API_KEY}")
    String API_KEY;

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

    public String[] getDistance(String[] src,String[] dest) throws IOException, InterruptedException, ApiException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        DistanceMatrix distanceMatrix = DistanceMatrixApi.getDistanceMatrix(context, src, dest)
                .mode(TravelMode.DRIVING)
                .await();

        String distance = distanceMatrix.rows[0].elements[0].distance.humanReadable;
        String duration = distanceMatrix.rows[0].elements[0].duration.humanReadable;

        System.out.println("Distance: " + distanceMatrix.rows[0].elements[0].distance.humanReadable);
        System.out.println("Duration: " + distanceMatrix.rows[0].elements[0].duration.humanReadable);

        return new String[]{distance, duration};
    }

    @Override
    @Transactional
    public Ride addRide(RideDto rideDto) throws IOException, InterruptedException, ApiException {

        String[] src = {rideDto.getPickupLocation()}; // Example: New York
        String[] dest = {rideDto.getDropLocation()}; // Example: Los Angeles

        String[] elements;
        if(src[0].isEmpty() || dest[0].isEmpty()){
            throw new UnexpectedException("Either Pickup or Drop location is empty");
        }else{
            elements = getDistance(src, dest);
        }
        elements[0] = elements[0].replaceAll("[^\\d.]","");

        Customer customer = customerRepository.findById(rideDto.getCustomer_id())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Cab cab = cabRepository.findById(rideDto.getCab_id()).orElseThrow(() -> new RuntimeException("Cab not found"));

        Driver driver = driverRepository.findById(rideDto.getDriver_id()).orElseThrow(() -> new RuntimeException("Driver not found"));

        double distance = Double.parseDouble(elements[0]);
        double fare = 0;
        if(cab.getSeatingCapacity() == 5){
            fare += (distance * FarePrice.FIVE_SEATER.getRatePerKm());
        }else if(cab.getSeatingCapacity() == 7){
            fare += (distance * FarePrice.SEVEN_SEATER.getRatePerKm());
        }

        Ride ride = new Ride();
        ride.setPickupLocation(rideDto.getPickupLocation());
        ride.setDropLocation(rideDto.getDropLocation());
        ride.setFare((int)fare);
        ride.setDistance(distance);
        ride.setDuration(elements[1]);
        ride.setBooking_status(true);
        ride.setCustomer(customer);
        ride.setCab(cab);
        ride.setDriver(driver);
        Ride savedRide = rideRepository.save(ride);

        Payment payment = new Payment();
        payment.setPaymentType(rideDto.getPaymentType());
        payment.setPaymentStatus("PENDING");
        payment.setRide(savedRide);
        payment.setCustomer(customer);
        paymentRepository.save(payment);

        return savedRide;
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
