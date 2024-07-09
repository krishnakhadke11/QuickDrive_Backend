package com.TransportationService.service;

import com.TransportationService.entity.DriverOperation;

public interface DriverOperationService {

    DriverOperation addDriverOperation(DriverOperation driverOperation);
    DriverOperation getDriverOperationById(int driverId);
    void deleteDriverOperation(int id);
}
