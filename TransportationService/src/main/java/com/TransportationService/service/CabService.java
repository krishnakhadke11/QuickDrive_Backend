package com.TransportationService.service;

import com.TransportationService.entity.Cab;
import com.TransportationService.entity.User;

import java.util.List;

public interface CabService {

     Cab addCab(Cab cab);

     List<Cab> findAllCabs();

     Cab findCabById(int id);

     Cab updateCab(Cab cab);

     void deleteCab(int id);

     User findCabsOwner(int cabId);
}
