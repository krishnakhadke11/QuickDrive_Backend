package com.TransportationService.repository;

import com.TransportationService.entity.PaymentStatus;
import com.TransportationService.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {
    List<Ride> findByCustomerId(int customerId);

    List<Ride> findByDriverId(int driverId);

    Ride findTopByDriverIdOrderByCreatedAtDesc(int driverId);

    @Query("SELECT AVG(r.rating) FROM Ride r where r.driver.id = :driverId ")
    Double getAverageRatingByDriverId(@Param("driverId") int driverId);
}
