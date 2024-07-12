package com.TransportationService.service;

import com.TransportationService.dto.request.PaymentDto;
import com.TransportationService.dto.request.PaymentUpdateDto;
import com.TransportationService.dto.request.PaymentUpdateStatusDto;
import com.TransportationService.entity.Payment;

import java.util.List;

public interface PaymentService {
    Payment addPayment(PaymentDto paymentDto);

    Payment findPaymentById(int id);

    List<Payment> findPaymentsByCustomerId(int customerId);

    Payment updatePayment(PaymentUpdateDto paymentUpdateDto);
}
