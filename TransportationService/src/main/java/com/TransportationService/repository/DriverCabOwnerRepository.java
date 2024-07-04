package com.TransportationService.repository;

import com.TransportationService.entity.DriverCabOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverCabOwnerRepository extends JpaRepository<DriverCabOwner, Integer> {
}
