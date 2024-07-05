package com.TransportationService.repository;

import com.TransportationService.entity.Cab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CabRepository extends JpaRepository<Cab, Integer> {
    List<Cab> findCabByUserId(int userId);
}
