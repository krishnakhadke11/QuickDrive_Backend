package com.TransportationService.service;

import com.TransportationService.dto.request.DriverOperationDto;
import com.TransportationService.dto.request.DriverOperationUpdateDto;
import com.TransportationService.entity.DriverOperation;

import java.util.List;

public interface DriverOperationService {

    DriverOperation addDriverOperation(DriverOperationDto driverOperationDto);

    List<DriverOperation> getAllDriverOperation();

    DriverOperation getDriverOperationByDriverId(int driverId);

    DriverOperation getDriverOperationById(int id);

    void deleteDriverOperation(int id);

    DriverOperation updateDriverOperation(DriverOperationUpdateDto driverOperationUpdateDto);
}
