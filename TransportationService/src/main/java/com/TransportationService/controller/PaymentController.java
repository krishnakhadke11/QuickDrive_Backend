package com.TransportationService.controller;

import com.TransportationService.dto.request.PaymentDto;
import com.TransportationService.dto.request.PaymentUpdateDto;
import com.TransportationService.dto.request.PaymentUpdateStatusDto;
import com.TransportationService.entity.Payment;
import com.TransportationService.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
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

    @Operation(summary = "Add a Payment", description = "Returns the newly added payment")
    @PostMapping("/payments")
    public ResponseEntity<Payment> addPayment(@RequestBody PaymentDto paymentDto) {
        Payment savedPayment = paymentService.addPayment(paymentDto);
        return ResponseEntity.ok(savedPayment);
    }

    @Operation(summary = "Get a payment by id", description = "Returns the payment as per the id")
    @GetMapping("/payments/{payment-id}")
    public ResponseEntity<Payment> getPayment(@PathVariable("payment-id") int paymentId) {
        Payment payment = paymentService.findPaymentById(paymentId);
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "Update the payment", description = "Returns the updated payment")
    @PutMapping("/payments")
    public ResponseEntity<Payment> updatePayment(@RequestBody PaymentUpdateDto paymentUpdateDto) {
        Payment updatedPayment = paymentService.updatePayment(paymentUpdateDto);
        return ResponseEntity.ok(updatedPayment);
    }

    @Operation(summary = "Update the Payment Status of the Payment", description = "Returns the updated payment")
    @PatchMapping("/payments/{payment-id}/payment-status")
    public ResponseEntity<Payment> updatePaymentStatus(@PathVariable("payment-id") int paymentId,@RequestBody PaymentUpdateStatusDto paymentUpdateStatusDto) {
        Payment updatedPayment = paymentService.updatePaymentStatus(paymentId,paymentUpdateStatusDto);
        return ResponseEntity.ok(updatedPayment);
    }
}
