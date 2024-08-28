package com.TransportationService.service.impl;

import com.TransportationService.dto.request.PaymentDto;
import com.TransportationService.dto.request.PaymentUpdateDto;
import com.TransportationService.dto.request.PaymentUpdateStatusDto;
import com.TransportationService.dto.response.EarningResponse;
import com.TransportationService.entity.Payment;
import com.TransportationService.entity.PaymentStatus;
import com.TransportationService.entity.PaymentType;
import com.TransportationService.entity.Ride;
import com.TransportationService.repository.CustomerRepository;
import com.TransportationService.repository.PaymentRepository;
import com.TransportationService.repository.RideRepository;
import com.TransportationService.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

        Payment payment = paymentRepository.findById(paymentUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        Ride ride = rideRepository.findById(paymentUpdateDto.getRide().getId())
                .orElseThrow(() -> new EntityNotFoundException("Ride not found"));

        Payment updatePayment = new Payment();
        updatePayment.setId(paymentUpdateDto.getId());
        updatePayment.setPaymentType(paymentUpdateDto.getPaymentType());
        updatePayment.setPaymentStatus(paymentUpdateDto.getPaymentStatus());
        updatePayment.setCreatedAt(payment.getCreatedAt());
        updatePayment.setRide(ride);

        return paymentRepository.save(updatePayment);
    }

    @Override
    @Transactional
    public Payment updatePaymentStatus(int paymentId,PaymentUpdateStatusDto paymentUpdateStatusDto) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        payment.setPaymentStatus(paymentUpdateStatusDto.getPaymentStatus());
        return paymentRepository.save(payment);
    }

    @Override
    public EarningResponse getMonthlyEarnings(int driverId) {
        PaymentStatus status = PaymentStatus.PAID;
        PaymentType typeCash = PaymentType.CASH;
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        if(!paymentRepository.existsByDriverId(driverId)){
            throw new EntityNotFoundException("No Payments Currently");
        }
        Double total = paymentRepository.findTotalEarningsByPaymentStatusAndCurrentMonthByDriverId(status,currentMonth,currentYear,driverId);
        Double cash = paymentRepository.findTotalEarningsByPaymentStatusAndPaymentTypeAndCurrentMonthAndDriverId(status,typeCash,currentMonth,currentYear,driverId);

        EarningResponse earningResponse = getEarningResponse(total, cash);
        return earningResponse;
    }

    private static @NotNull EarningResponse getEarningResponse(Double total, Double cash) {
        if(total != null && cash == null){
            cash = 0.0;
        }

        double online = 0.0;
        double cashPercentage = 0.0;
        double onlinePercentage = 0.0;
        if(total != null) {
            online = total - cash;
            cashPercentage = (cash / total) * 100;
            onlinePercentage = 100 - cashPercentage;
        }

        EarningResponse earningResponse = new EarningResponse();
        earningResponse.setTotal(total);
        earningResponse.setCash(cash);
        earningResponse.setOnline(online);
        earningResponse.setCashPercentage(cashPercentage);
        earningResponse.setOnlinePercentage(onlinePercentage);
        return earningResponse;
    }

}
