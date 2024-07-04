package com.TransportationService.service;

import com.TransportationService.entity.Cab;

public interface CabSerive {

    public Cab findCabById(int id);

    public Cab addCab(Cab cab);
}
