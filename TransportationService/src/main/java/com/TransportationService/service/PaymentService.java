package com.TransportationService.service;

import com.TransportationService.dto.request.PaymentUpdateStatusDto;
import com.TransportationService.entity.Payment;

import java.util.List;

public interface PaymentService {
    Payment addPayment(Payment payment);

    Payment findPaymentById(int id);

    List<Payment> findPaymentsByCustomerId(int customerId);

    Payment updatePayment(Payment payment);

    Payment updatePaymentStatus(PaymentUpdateStatusDto paymentUpdateStatusDto);
}
