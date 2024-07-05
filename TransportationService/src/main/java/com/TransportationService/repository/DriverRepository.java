package com.TransportationService.repository;

import com.TransportationService.entity.Cab;
import com.TransportationService.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {
}
