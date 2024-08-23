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
    @PostMapping("/payment")
    public ResponseEntity<Payment> addPayment(@RequestBody PaymentDto paymentDto) {
        Payment savedPayment = paymentService.addPayment(paymentDto);
        return ResponseEntity.ok(savedPayment);
    }

    @Operation(summary = "Get a payment by id", description = "Returns the payment as per the id")
    @GetMapping("/payment/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable int id) {
        Payment payment = paymentService.findPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "Update the payment", description = "Returns the updated payment")
    @PutMapping("/payment")
    public ResponseEntity<Payment> updatePayment(@RequestBody PaymentUpdateDto paymentUpdateDto) {
        Payment updatedPayment = paymentService.updatePayment(paymentUpdateDto);
        return ResponseEntity.ok(updatedPayment);
    }
}
