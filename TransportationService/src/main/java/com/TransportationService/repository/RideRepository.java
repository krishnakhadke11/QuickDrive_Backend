package com.TransportationService.repository;

import com.TransportationService.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {
    List<Ride> findRideByCustomerId(int customerId);

    Ride findTopByDriverIdOrderByCreatedAtDesc(int driverId);
}
