package com.TransportationService.controller;

import com.TransportationService.entity.DriverOperation;
import com.TransportationService.service.DriverOperationService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Create the operation", description = "Returns the created driveroperation")
    @PostMapping("/driveroperation")
    public ResponseEntity<DriverOperation> addDriverOperation(@RequestBody DriverOperation driverOperation){
        DriverOperation savedDriverOperation =  driverOperationService.addDriverOperation(driverOperation);
        return ResponseEntity.ok().body(savedDriverOperation);
    }

    @Operation(summary = "Get all driveroperation", description = "Returns the list of driveroperation")
    @GetMapping("/driveroperation")
    public ResponseEntity<List<DriverOperation>> getAllDriverOperations(){
        List<DriverOperation> allDriverOperation =  driverOperationService.getAllDriverOperation();
        return ResponseEntity.ok().body(allDriverOperation);
    }

    @Operation(summary = "Get driver operation by id", description = "Returns the driveroperation as per the id")
    @GetMapping("/driveroperation/{id}")
    public ResponseEntity<DriverOperation> getDriverOperationById(@PathVariable int id){
        DriverOperation driverOperation =  driverOperationService.getDriverOperationById(id);
        return ResponseEntity.ok().body(driverOperation);
    }

    @Operation(summary = "Update the driveroperation", description = "Returns the updated driveroperation")
    @PutMapping("/driveroperation")
    public ResponseEntity<DriverOperation> updateDriverOperation(@RequestBody DriverOperation driverOperation){
        DriverOperation savedDriverOperation =  driverOperationService.updateDriverOperation(driverOperation);
        return ResponseEntity.ok().body(savedDriverOperation);
    }

    @Operation(summary = "Delete a driveroperation by id", description = "Returns the confirmation string")
    @DeleteMapping("/driveroperation/{id}")
    public ResponseEntity<String> deleteDriverOperation(@PathVariable int id){
        driverOperationService.deleteDriverOperation(id);
        return ResponseEntity.ok().body("Driver Operation is deleted");
    }
}
