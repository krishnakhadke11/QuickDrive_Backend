package com.TransportationService.controller;

import com.TransportationService.entity.DriverOperation;
import com.TransportationService.service.DriverOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DriverOperationController {
    DriverOperationService driverOperationService;

    @Autowired
    public DriverOperationController(DriverOperationService driverOperationService){
        this.driverOperationService = driverOperationService;
    }

    @PostMapping("/driveroperation")
    public DriverOperation addDriverOperation(@RequestBody DriverOperation driverOperation){
        return driverOperationService.addDriverOperation(driverOperation);
    }

    @DeleteMapping("/driveroperation/{id}")
    public void deleteDriverOperation(@PathVariable int id){
        driverOperationService.deleteDriverOperation(id);
    }
}
