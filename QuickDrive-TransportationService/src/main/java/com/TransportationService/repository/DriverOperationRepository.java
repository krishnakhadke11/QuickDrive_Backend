package com.TransportationService.repository;

import com.TransportationService.entity.DriverOperation;
import com.TransportationService.entity.SeatingCapacity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverOperationRepository extends JpaRepository<DriverOperation, Integer> {
    DriverOperation findDriverOperationByDriverId(int driverId);

    boolean existsDriverOperationByDriverId(int driverId);

    void deleteDriverOperationByDriverId(int driverId);

    @Query("SELECT d FROM DriverOperation d WHERE d.driver.id = :driverId AND d.cab.seatingCapacity = :seatingCapacity")
    DriverOperation findByDriverIdAndCabSeatingCapacity(
            @Param("driverId") int driverId,
            @Param("seatingCapacity") SeatingCapacity seatingCapacity);
}
