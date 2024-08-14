package com.TransportationService.repository;

import com.TransportationService.entity.RideRequest;
import com.TransportationService.entity.SeatingCapacity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest,Integer> {
    List<RideRequest> findAllRideRequestByCustomerId(int customerId);

    List<RideRequest> findByRequestTimeBefore(LocalDateTime time);

    List<RideRequest> findAllRideRequestBySeatingCapacity(SeatingCapacity seatingCapacity);
}
