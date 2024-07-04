package com.TransportationService.repository;

import com.TransportationService.entity.AdminCabOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminCabOwnerRepository extends JpaRepository<AdminCabOwner,Integer> {
}
