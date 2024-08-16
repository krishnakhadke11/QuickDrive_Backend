package com.TransportationService.service.impl;

import com.TransportationService.dto.request.PaymentDto;
import com.TransportationService.dto.request.PaymentUpdateDto;
import com.TransportationService.entity.Payment;
import com.TransportationService.entity.Ride;
import com.TransportationService.repository.CustomerRepository;
import com.TransportationService.repository.PaymentRepository;
import com.TransportationService.repository.RideRepository;
import com.TransportationService.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final CustomerRepository customerRepository;
    private final RideRepository rideRepository;
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, CustomerRepository customerRepository, RideRepository rideRepository) {
        this.paymentRepository = paymentRepository;
        this.customerRepository = customerRepository;
        this.rideRepository = rideRepository;
    }

    @Override
    @Transactional
    public Payment addPayment(PaymentDto paymentDto) {

        Ride ride = rideRepository.findById(paymentDto.getRide().getId())
                .orElseThrow(() -> new EntityNotFoundException("Ride not found"));

        Payment payment = new Payment();
        payment.setPaymentType(paymentDto.getPaymentType());
        payment.setPaymentStatus(paymentDto.getPaymentStatus());
        payment.setRide(ride);

        return paymentRepository.save(payment);
    }

    @Override
    public Payment findPaymentById(int id) {
        return paymentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payment not found"));
    }

    @Override
    public List<Payment> findPaymentsByCustomerId(int customerId) {
        if(!customerRepository.existsById(customerId)){
            throw new EntityNotFoundException("Customer not found");
        }
        return paymentRepository.findByRideCustomerId(customerId);
    }

    @Override
    @Transactional
    public Payment updatePayment(PaymentUpdateDto paymentUpdateDto) {
        if(!paymentRepository.existsById(paymentUpdateDto.getId())){
            throw new EntityNotFoundException("Payment Not Found");
        }

        Ride ride = rideRepository.findById(paymentUpdateDto.getRide().getId())
                .orElseThrow(() -> new EntityNotFoundException("Ride not found"));

        Payment payment = new Payment();
        payment.setId(paymentUpdateDto.getId());
        payment.setPaymentType(paymentUpdateDto.getPaymentType());
        payment.setPaymentStatus(paymentUpdateDto.getPaymentStatus());
        payment.setRide(ride);

        return paymentRepository.save(payment);
    }
}
