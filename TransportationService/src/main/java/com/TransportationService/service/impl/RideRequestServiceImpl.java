package com.TransportationService.service.impl;

import com.TransportationService.dto.request.RideRequestDto;
import com.TransportationService.dto.response.RideRequestResponseDto;
import com.TransportationService.dto.response.RideRequestWithRideDto;
import com.TransportationService.entity.*;
import com.TransportationService.repository.*;
import com.TransportationService.service.RideRequestService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RideRequestServiceImpl implements RideRequestService {

    private RideRequestRepository rideRequestRepository;
    private CustomerRepository customerRepository;
    private DriverRepository driverRepository;
    private DriverOperationRepository driverOperationRepository;
    private RideRepository rideRepository;
    private PaymentRepository paymentRepository;

    @Autowired
    public RideRequestServiceImpl(RideRequestRepository rideRequestRepository, CustomerRepository customerRepository, DriverRepository driverRepository, DriverOperationRepository driverOperationRepository, RideRepository rideRepository, PaymentRepository paymentRepository) {
        this.rideRequestRepository = rideRequestRepository;
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
        this.driverOperationRepository = driverOperationRepository;
        this.rideRepository = rideRepository;
        this.paymentRepository = paymentRepository;
    }

//    @Scheduled(fixedRate = 600000) // Check every minute
//    public void cancelPendingRequests() {
//        LocalDateTime twoMinutesAgo = LocalDateTime.now().minusMinutes(2);
//        List<RideRequest> pendingRequests = rideRequestRepository.findByStatusAndRequestTimeBefore(BookingStatus.PENDING,twoMinutesAgo);
//
//        for (RideRequest request : pendingRequests) {
//            rideRequestRepository.delete(request);
//        }
//    }

    @Override
    @Transactional
    public RideRequest addRideRequest(RideRequestDto rideRequest, int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        RideRequest newRideReq = new RideRequest();
        newRideReq.setPickupLocation(rideRequest.getPickupLocation());
        newRideReq.setPickupName(rideRequest.getPickupName());
        newRideReq.setDropLocation(rideRequest.getDropLocation());
        newRideReq.setDropName(rideRequest.getDropName());
        newRideReq.setDistance(rideRequest.getDistance());
        newRideReq.setDuration(rideRequest.getDuration());
        newRideReq.setFare(rideRequest.getFare());
        newRideReq.setPaymentType(rideRequest.getPaymentType());
        newRideReq.setSeatingCapacity(rideRequest.getSeatingCapacity());
        newRideReq.setBookingStatus(BookingStatus.PENDING);
        newRideReq.setRequestTime(LocalDateTime.now());
        newRideReq.setCustomer(customer);

        return rideRequestRepository.save(newRideReq);
    }

    @Override
    @Transactional
    public List<RideRequest> getAllRideRequest() {
        return rideRequestRepository.findAll();
    }

    @Override
    public RideRequestResponseDto getRideRequestById(int id) {
        RideRequest rideRequest=  rideRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ride Request Not Found"));

        RideRequestResponseDto newRideReq = new RideRequestResponseDto();

        newRideReq.setId(rideRequest.getId());
        newRideReq.setPickupLocation(rideRequest.getPickupLocation());
        newRideReq.setPickupName(rideRequest.getPickupName());
        newRideReq.setDropLocation(rideRequest.getDropLocation());
        newRideReq.setDropName(rideRequest.getDropName());
        newRideReq.setDistance(rideRequest.getDistance());
        newRideReq.setDuration(rideRequest.getDuration());
        newRideReq.setFare(rideRequest.getFare());
        newRideReq.setPaymentType(rideRequest.getPaymentType());
        newRideReq.setSeatingCapacity(rideRequest.getSeatingCapacity());
        newRideReq.setBookingStatus(rideRequest.getBookingStatus());
        newRideReq.setRequestTime(LocalDateTime.now());
        newRideReq.setCustomer(rideRequest.getCustomer());

        return newRideReq;
    }

    @Override
    public List<RideRequest> getAllCustomersRideRequest(int customerId) {
        return rideRequestRepository.findAllRideRequestByCustomerId(customerId);
    }

    @Override
    public String deleteRideRequest(int rideReqId) {
        if(!rideRequestRepository.existsById(rideReqId)){
            throw new EntityNotFoundException("Ride Request Not Found");
        }
        rideRepository.deleteById(rideReqId);
        return "Ride Request Deleted Successfully";
    }

    @Override
    @Transactional
    public Ride acceptRideRequest(int rideReqId,int driverId) {

        RideRequest rideRequest = rideRequestRepository.findById(rideReqId)
                .orElseThrow(() -> new EntityNotFoundException("Ride Request Not Found"));

        DriverOperation driverOperation = driverOperationRepository.findByDriverIdAndCabSeatingCapacity(driverId,rideRequest.getSeatingCapacity());

        Ride ride = getRide(driverOperation, rideRequest);

        Ride savedRide = rideRepository.save(ride);

        rideRequest.setRide(savedRide);
        rideRequest.setBookingStatus(BookingStatus.ACCEPTED);
        System.out.println(rideRequest);
        rideRequestRepository.save(rideRequest);

        Payment payment = new Payment();
        payment.setPaymentType(rideRequest.getPaymentType());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setRide(savedRide);

        paymentRepository.save(payment);

        return savedRide;
    }

    @Override
    public RideRequestWithRideDto findByIdWithRide(int rideReqId) {
        RideRequest rideRequest =  rideRequestRepository.findById(rideReqId)
                .orElseThrow(() -> new EntityNotFoundException("Ride Request Not Found"));

        RideRequestWithRideDto newRideReq = new RideRequestWithRideDto();

        newRideReq.setId(rideRequest.getId());
        newRideReq.setPickupLocation(rideRequest.getPickupLocation());
        newRideReq.setPickupName(rideRequest.getPickupName());
        newRideReq.setDropLocation(rideRequest.getDropLocation());
        newRideReq.setDropName(rideRequest.getDropName());
        newRideReq.setDistance(rideRequest.getDistance());
        newRideReq.setDuration(rideRequest.getDuration());
        newRideReq.setFare(rideRequest.getFare());
        newRideReq.setPaymentType(rideRequest.getPaymentType());
        newRideReq.setSeatingCapacity(rideRequest.getSeatingCapacity());
        newRideReq.setBookingStatus(rideRequest.getBookingStatus());
        newRideReq.setRequestTime(LocalDateTime.now());
        newRideReq.setRide(rideRequest.getRide());

        return  newRideReq;
    }

    private static @NotNull Ride getRide(DriverOperation driverOperation, RideRequest rideRequest) {
        Cab cab;
        if(driverOperation != null){
            cab = driverOperation.getCab();
        }else{
            throw new RuntimeException("Either Driver is not operational or the seating capacity does not match with the ride request");
        }

        Ride ride = new Ride();
        ride.setPickupLocation(rideRequest.getPickupLocation());
        ride.setPickupName(rideRequest.getPickupName());
        ride.setDropLocation(rideRequest.getDropLocation());
        ride.setDropName(rideRequest.getDropName());
        ride.setFare(rideRequest.getFare());
        ride.setDistance(rideRequest.getDistance());
        ride.setDuration(rideRequest.getDuration());
        ride.setPaymentType(rideRequest.getPaymentType());
        ride.setCustomer(rideRequest.getCustomer());
        ride.setDriver(driverOperation.getDriver());
        ride.setCab(cab);
        return ride;
    }
}
