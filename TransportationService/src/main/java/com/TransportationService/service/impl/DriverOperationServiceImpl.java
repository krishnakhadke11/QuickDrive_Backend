package com.TransportationService.service.impl;

import com.TransportationService.entity.Cab;
import com.TransportationService.entity.Driver;
import com.TransportationService.entity.DriverOperation;
import com.TransportationService.repository.CabRepository;
import com.TransportationService.repository.DriverOperationRepository;
import com.TransportationService.repository.DriverRepository;
import com.TransportationService.service.DriverOperationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverOperationServiceImpl implements DriverOperationService {
    DriverOperationRepository driverOperationRepository;
    DriverRepository driverRepository;
    CabRepository cabRepository;

    @Autowired
    public DriverOperationServiceImpl(DriverOperationRepository driverOperationRepository,DriverRepository driverRepository
                                        ,CabRepository cabRepository){
        this.driverOperationRepository = driverOperationRepository;
        this.driverRepository = driverRepository;
        this.cabRepository = cabRepository;
    }

    @Override
    @Transactional
    public DriverOperation addDriverOperation(DriverOperation driverOperation) {
        System.out.println("In add DriverOperation"+driverOperation);
        int driverId = driverOperation.getDriver().getId();
        int cabId = driverOperation.getCab().getId();
        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        Cab cab = cabRepository.findById(cabId).orElseThrow(() -> new EntityNotFoundException("Cab not found"));

        driverOperation.setDriver(driver);
        driverOperation.setCab(cab);
        return driverOperationRepository.save(driverOperation);
    }

    @Override
    public List<DriverOperation> getAllDriverOperation() {
        return driverOperationRepository.findAll();
    }

    @Override
    public DriverOperation getDriverOperationByDriverId(int driverId) {
        DriverOperation driverOperation = driverOperationRepository.findDriverOperationByDriverId(driverId);
        return driverOperation;
    }

    @Override
    @Transactional
    public void deleteDriverOperation(int driverId) {
        if(!driverOperationRepository.existsDriverOperationByDriverId(driverId)){
            throw new EntityNotFoundException("Record not found");
        }
        driverOperationRepository.deleteDriverOperationByDriverId(driverId);
    }

    @Override
    public DriverOperation updateDriverOperation(DriverOperation driverOperation) {
        int id = driverOperation.getId();
        if(!driverOperationRepository.existsById(id)){
            throw new EntityNotFoundException("Record not found");
        }
        return driverOperationRepository.save(driverOperation);
    }
}
