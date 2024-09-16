package com.TransportationService.service;

import com.TransportationService.dto.request.CabDto;
import com.TransportationService.dto.request.CabUpdateDto;
import com.TransportationService.entity.Cab;
import com.TransportationService.entity.User;

import java.util.List;

public interface CabService {

     Cab addCab(CabDto cabDto);

     List<Cab> findAllCabs();

     Cab findCabById(int id);

     Cab updateCab(CabUpdateDto cabUpdateDto);

     void deleteCab(int id);

     User findCabsOwner(int cabId);
}
