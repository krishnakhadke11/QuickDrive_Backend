package com.TransportationService.service.impl;

import com.TransportationService.dto.request.DriverOperationDto;
import com.TransportationService.dto.request.DriverOperationUpdateDto;
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
    public DriverOperation addDriverOperation(DriverOperationDto driverOperationDto,int driverId) {
//        System.out.println("In add DriverOperation"+driverOperation);

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        Cab cab = cabRepository.findById(driverOperationDto.getCab().getId())
                .orElseThrow(() -> new EntityNotFoundException("Cab not found"));

        DriverOperation driverOperation = new DriverOperation();
        driverOperation.setStartTime(driverOperationDto.getStartTime());
        driverOperation.setEndTime(driverOperationDto.getEndTime());
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
        if(!driverOperationRepository.existsDriverOperationByDriverId(driverId)){
            throw new EntityNotFoundException("Driver Is Not Operational");
        }
        DriverOperation driverOperation = driverOperationRepository.findDriverOperationByDriverId(driverId);
        return driverOperation;
    }

    @Override
    public DriverOperation getDriverOperationById(int id) {
        DriverOperation driverOperation = driverOperationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DriverOperation not found"));
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
    public DriverOperation updateDriverOperation(DriverOperationUpdateDto driverOperationUpdateDto) {

        if(!driverOperationRepository.existsById(driverOperationUpdateDto.getId())){
            throw new EntityNotFoundException("Record not found");
        }

        Driver driver = driverRepository.findById(driverOperationUpdateDto.getDriver().getId())
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        Cab cab = cabRepository.findById(driverOperationUpdateDto.getCab().getId())
                .orElseThrow(() -> new EntityNotFoundException("Cab not found"));

        DriverOperation driverOperation = new DriverOperation();
        driverOperation.setStartTime(driverOperationUpdateDto.getStartTime());
        driverOperation.setEndTime(driverOperationUpdateDto.getEndTime());
        driverOperation.setDriver(driver);
        driverOperation.setCab(cab);

        return driverOperationRepository.save(driverOperation);
    }
}
