package com.TransportationService.repository;

import com.TransportationService.entity.DriverOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverOperationRepository extends JpaRepository<DriverOperation, Integer> {
    DriverOperation findDriverOperationByDriverId(int driverId);
}
