package com.TransportationService.repository;

import com.TransportationService.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByRideCustomerId(int customerId);

    Payment findByRideId(int rideId);
}
