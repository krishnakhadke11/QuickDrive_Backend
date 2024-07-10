package com.TransportationService.service;

import com.TransportationService.entity.DriverOperation;

import java.util.List;

public interface DriverOperationService {

    DriverOperation addDriverOperation(DriverOperation driverOperation);

    List<DriverOperation> getAllDriverOperation();

    DriverOperation getDriverOperationByDriverId(int driverId);

    DriverOperation getDriverOperationById(int id);

    void deleteDriverOperation(int id);

    DriverOperation updateDriverOperation(DriverOperation driverOperation);
}
