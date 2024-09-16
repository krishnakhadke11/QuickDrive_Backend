package com.TransportationService.service.impl;

import com.TransportationService.dto.request.BookingStatusUpdateRequest;
import com.TransportationService.dto.request.RideIdDto;
import com.TransportationService.dto.request.RideRequestDto;
import com.TransportationService.dto.request.RideRequestUpdateDto;
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
        newRideReq.setCustomer(customer);

        return rideRequestRepository.save(newRideReq);
    }

    @Override
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
        newRideReq.setCreatedAt(rideRequest.getCreatedAt());
        newRideReq.setCustomer(rideRequest.getCustomer());

        return newRideReq;
    }

    @Override
    public List<RideRequest> getAllCustomersRideRequest(int customerId) {
        return rideRequestRepository.findAllRideRequestByCustomerId(customerId);
    }

    @Override
    @Transactional
    public String deleteRideRequest(int rideReqId) {
        if(!rideRequestRepository.existsById(rideReqId)){
            throw new EntityNotFoundException("Ride Request Not Found");
        }
        rideRequestRepository.deleteById(rideReqId);
        return "Ride Request Deleted Successfully";
    }

//    @Override
//    @Transactional
//    public Ride acceptRideRequest(int rideReqId,int driverId) {
//
//        RideRequest rideRequest = rideRequestRepository.findById(rideReqId)
//                .orElseThrow(() -> new EntityNotFoundException("Ride Request Not Found"));
//
//        DriverOperation driverOperation = driverOperationRepository.findByDriverIdAndCabSeatingCapacity(driverId,rideRequest.getSeatingCapacity());
//
//        Ride ride = getRide(driverOperation, rideRequest);
//
//        Ride savedRide = rideRepository.save(ride);
//
//        rideRequest.setRide(savedRide);
//        rideRequest.setBookingStatus(BookingStatus.ACCEPTED);
//        System.out.println(rideRequest);
//        rideRequestRepository.save(rideRequest);
//
//        Payment payment = new Payment();
//        payment.setPaymentType(rideRequest.getPaymentType());
//        payment.setPaymentStatus(PaymentStatus.PENDING);
//        payment.setRide(savedRide);
//        paymentRepository.save(payment);
//
//        driverOperation.setCabStatus(CabStatus.HIRED);
//        driverOperationRepository.save(driverOperation);
//
//        return savedRide;
//    }

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
        newRideReq.setCreatedAt(rideRequest.getCreatedAt());
        newRideReq.setRide(rideRequest.getRide());

        return  newRideReq;
    }

    @Override
    @Transactional
    public RideRequest updateRideRequest(RideRequestUpdateDto rideRequestUpdateDto) {
        RideRequest rideRequest = rideRequestRepository.findById(rideRequestUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Ride Request Not Found"));

        Ride ride = rideRepository.findById(rideRequestUpdateDto.getRide().getId())
                .orElseThrow(() -> new EntityNotFoundException("Ride Not Found"));

        rideRequest.setPickupLocation(rideRequestUpdateDto.getPickupLocation());
        rideRequest.setPickupName(rideRequestUpdateDto.getPickupName());
        rideRequest.setDropLocation(rideRequestUpdateDto.getDropLocation());
        rideRequest.setDropName(rideRequestUpdateDto.getDropName());
        rideRequest.setDistance(rideRequestUpdateDto.getDistance());
        rideRequest.setDuration(rideRequestUpdateDto.getDuration());
        rideRequest.setFare(rideRequestUpdateDto.getFare());
        rideRequest.setPaymentType(rideRequestUpdateDto.getPaymentType());
        rideRequest.setSeatingCapacity(rideRequestUpdateDto.getSeatingCapacity());
        rideRequest.setBookingStatus(rideRequestUpdateDto.getBookingStatus());
        rideRequest.setRide(ride);

        return rideRequestRepository.save(rideRequest);
    }

    @Override
    @Transactional
    public RideRequest patchRideOfRideRequest(int rideReqId,RideIdDto rideIdDto) {
        RideRequest rideRequest = rideRequestRepository.findById(rideReqId)
                .orElseThrow(() -> new EntityNotFoundException("Ride Request Not Found"));

        Ride ride = rideRepository.findById(rideIdDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Ride Not Found"));

        rideRequest.setRide(ride);

        return rideRequestRepository.save(rideRequest);
    }

    @Override
    @Transactional
    public RideRequest patchBookingStatusOfRideRequest(int rideRequestId, BookingStatusUpdateRequest bookingStatusUpdateRequest) {
        RideRequest rideRequest = rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new EntityNotFoundException("Ride Request Not Found"));

        rideRequest.setBookingStatus(bookingStatusUpdateRequest.getBookingStatus());
        return rideRequestRepository.save(rideRequest);
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
