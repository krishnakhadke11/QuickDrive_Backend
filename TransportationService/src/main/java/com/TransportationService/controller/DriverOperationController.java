package com.TransportationService.controller;

import com.TransportationService.entity.DriverOperation;
import com.TransportationService.service.DriverOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DriverOperationController {
    DriverOperationService driverOperationService;

    @Autowired
    public DriverOperationController(DriverOperationService driverOperationService){
        this.driverOperationService = driverOperationService;
    }

    @PostMapping("/driveroperation")
    public ResponseEntity<DriverOperation> addDriverOperation(@RequestBody DriverOperation driverOperation){
        DriverOperation savedDriverOperation =  driverOperationService.addDriverOperation(driverOperation);
        return ResponseEntity.ok().body(savedDriverOperation);
    }

    @GetMapping("/driveroperation")
    public ResponseEntity<List<DriverOperation>> getAllDriverOperations(){
        List<DriverOperation> allDriverOperation =  driverOperationService.getAllDriverOperation();
        return ResponseEntity.ok().body(allDriverOperation);
    }

    @GetMapping("/driveroperation/{id}")
    public ResponseEntity<DriverOperation> getDriverOperationById(@PathVariable int id){
        DriverOperation driverOperation =  driverOperationService.getDriverOperationById(id);
        return ResponseEntity.ok().body(driverOperation);
    }

    @PutMapping("/driveroperation")
    public ResponseEntity<DriverOperation> updateDriverOperation(@RequestBody DriverOperation driverOperation){
        DriverOperation savedDriverOperation =  driverOperationService.updateDriverOperation(driverOperation);
        return ResponseEntity.ok().body(savedDriverOperation);
    }

    @DeleteMapping("/driveroperation/{id}")
    public ResponseEntity<String> deleteDriverOperation(@PathVariable int id){
        driverOperationService.deleteDriverOperation(id);
        return ResponseEntity.ok().body("Driver Operation is deleted");
    }
}
