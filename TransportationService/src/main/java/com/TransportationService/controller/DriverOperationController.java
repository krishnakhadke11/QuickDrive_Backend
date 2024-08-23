package com.TransportationService.controller;

import com.TransportationService.dto.request.DriverOperationDto;
import com.TransportationService.dto.request.DriverOperationUpdateDto;
import com.TransportationService.entity.DriverOperation;
import com.TransportationService.service.DriverOperationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    public ResponseEntity<DriverOperation> addDriverOperation(@Valid @RequestBody DriverOperationDto driverOperationDto, HttpServletRequest req){
        Integer driverId = (Integer) req.getAttribute("id");
        DriverOperation savedDriverOperation =  driverOperationService.addDriverOperation(driverOperationDto,driverId);
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

//    @Operation(summary = "Check if driver is Operational", description = "Returns Boolean Value")
//    @GetMapping("/driveroperation/check")
//    public ResponseEntity<DriverOperation> checkIfOperational(HttpServletRequest req){
//        Integer id = (Integer)req.getAttribute("id");
//        DriverOperation driverOps =  driverOperationService.getDriverOperationByDriverId(id);
//        return ResponseEntity.ok().body(driverOps);
//    }

    @Operation(summary = "Update the driveroperation", description = "Returns the updated driveroperation")
    @PutMapping("/driveroperation")
    public ResponseEntity<DriverOperation> updateDriverOperation(@Valid @RequestBody DriverOperationUpdateDto driverOperationUpdateDto){
        DriverOperation savedDriverOperation =  driverOperationService.updateDriverOperation(driverOperationUpdateDto);
        return ResponseEntity.ok().body(savedDriverOperation);
    }

    @Operation(summary = "Delete a driveroperation by id", description = "Returns the confirmation string")
    @DeleteMapping("/driveroperation/{id}")
    public ResponseEntity<String> deleteDriverOperation(@PathVariable int id){
        driverOperationService.deleteDriverOperation(id);
        return ResponseEntity.ok().body("Driver Operation is deleted");
    }
}
