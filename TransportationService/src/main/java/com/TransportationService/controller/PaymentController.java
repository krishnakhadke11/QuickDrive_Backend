package com.TransportationService.controller;

import com.TransportationService.dto.request.PaymentUpdateStatusDto;
import com.TransportationService.entity.Payment;
import com.TransportationService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment")
    public ResponseEntity<Payment> addPayment(@RequestBody Payment payment) {
        paymentService.addPayment(payment);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/payment/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable int id) {
        Payment payment = paymentService.findPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/payment/customer/{id}")
    public ResponseEntity<List<Payment>> getAllPayments(@PathVariable int id) {
        List<Payment> payments = paymentService.findPaymentsByCustomerId(id);
        return ResponseEntity.ok(payments);
    }

    @PutMapping("/payment")
    public ResponseEntity<Payment> updatePayment(@RequestBody Payment payment) {
        paymentService.updatePayment(payment);
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/payment/updatestatus")
    public ResponseEntity<Payment> updatePaymentStatus(@RequestBody PaymentUpdateStatusDto paymentUpdateStatusDto) {
        Payment payment = paymentService.updatePaymentStatus(paymentUpdateStatusDto);
        return ResponseEntity.ok(payment);
    }
}
