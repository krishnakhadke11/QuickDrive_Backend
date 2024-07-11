package com.TransportationService.controller;

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
    public ResponseEntity<Payment> addPayment(@RequestBody Payment payment) {
        paymentService.addPayment(payment);
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "Get a payment by id", description = "Returns the payment as per the id")
    @GetMapping("/payment/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable int id) {
        Payment payment = paymentService.findPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "Get all the payments of a customer", description = "Returns the list of payments of a customer")
    @GetMapping("/payment/customer")
    public ResponseEntity<List<Payment>> getAllPaymentsByCustomerId(HttpServletRequest req) {
        Integer id = (Integer) req.getAttribute("id");
        List<Payment> payments = paymentService.findPaymentsByCustomerId(id);
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Update the payment", description = "Returns the updated payment")
    @PutMapping("/payment")
    public ResponseEntity<Payment> updatePayment(@RequestBody Payment payment) {
        paymentService.updatePayment(payment);
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "Update the Payment status", description = "Returns the updated payment")
    @PutMapping("/payment/status")
    public ResponseEntity<Payment> updatePaymentStatus(@RequestBody PaymentUpdateStatusDto paymentUpdateStatusDto) {
        Payment payment = paymentService.updatePaymentStatus(paymentUpdateStatusDto);
        return ResponseEntity.ok(payment);
    }
}
