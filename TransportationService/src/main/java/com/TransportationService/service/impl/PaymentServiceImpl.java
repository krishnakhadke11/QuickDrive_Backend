package com.TransportationService.service.impl;

import com.TransportationService.dto.request.PaymentUpdateStatusDto;
import com.TransportationService.entity.Customer;
import com.TransportationService.entity.Payment;
import com.TransportationService.entity.PaymentStatus;
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
    public Payment addPayment(Payment payment) {
        //Customer customer = customerRepository.findById(payment.getCustomer().getId())
        //        .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        Ride ride = rideRepository.findById(payment.getRide().getId())
                .orElseThrow(() -> new EntityNotFoundException("Ride not found"));

//        payment.setCustomer(customer);
        payment.setRide(ride);
        Payment savedPayment =  paymentRepository.save(payment);

        return savedPayment;
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
        return paymentRepository.findPaymentsByRideCustomerId(customerId);
    }

    @Override
    @Transactional
    public Payment updatePayment(Payment payment) {
        if(!paymentRepository.existsById(payment.getId())){
            throw new EntityNotFoundException("Payment Not Found");
        }
        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public Payment updatePaymentStatus(PaymentUpdateStatusDto paymentUpdateStatusDto) {
        System.out.println(paymentUpdateStatusDto);
        Payment updatePayment = paymentRepository.findById(paymentUpdateStatusDto.getPaymentId()).orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        System.out.println(updatePayment);
        updatePayment.setPaymentStatus(paymentUpdateStatusDto.getPaymentStatus());

        return paymentRepository.save(updatePayment);
    }
}
