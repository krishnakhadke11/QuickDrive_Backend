package com.TransportationService.controller;

import com.TransportationService.dto.request.CabStatusUpdateRequest;
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
    @PostMapping("/driveroperations")
    public ResponseEntity<DriverOperation> addDriverOperation(@Valid @RequestBody DriverOperationDto driverOperationDto, HttpServletRequest req){
        Integer driverId = (Integer) req.getAttribute("id");
        DriverOperation savedDriverOperation =  driverOperationService.addDriverOperation(driverOperationDto,driverId);
        return ResponseEntity.ok().body(savedDriverOperation);
    }

    @Operation(summary = "Get all driveroperation", description = "Returns the list of driveroperation")
    @GetMapping("/driveroperations")
    public ResponseEntity<List<DriverOperation>> getAllDriverOperations(){
        List<DriverOperation> allDriverOperation =  driverOperationService.getAllDriverOperation();
        return ResponseEntity.ok().body(allDriverOperation);
    }

    @Operation(summary = "Get driver operation by id", description = "Returns the driveroperation as per the id")
    @GetMapping("/driveroperations/{driverOps-id}")
    public ResponseEntity<DriverOperation> getDriverOperationById(@PathVariable("driverOps-id") int driverOpsId){
        DriverOperation driverOperation =  driverOperationService.getDriverOperationById(driverOpsId);
        return ResponseEntity.ok().body(driverOperation);
    }

    @Operation(summary = "Update the driveroperation", description = "Returns the updated driveroperation")
    @PutMapping("/driveroperations")
    public ResponseEntity<DriverOperation> updateDriverOperation(@Valid @RequestBody DriverOperationUpdateDto driverOperationUpdateDto){
        DriverOperation savedDriverOperation =  driverOperationService.updateDriverOperation(driverOperationUpdateDto);
        return ResponseEntity.ok().body(savedDriverOperation);
    }

    @Operation(summary = "Update the driveroperation Status", description = "Returns the updated driveroperation")
    @PatchMapping("/driveroperations/{driverOps-id}/status")
    public ResponseEntity<DriverOperation> updateDriverOperation(@PathVariable("driverOps-id") int driverOpsId, @RequestBody CabStatusUpdateRequest cabStatusUpdateRequest){

        System.out.println("driverops Controller : "+ cabStatusUpdateRequest);
        DriverOperation savedDriverOperation =  driverOperationService.updateStatus(driverOpsId,cabStatusUpdateRequest);
        return ResponseEntity.ok().body(savedDriverOperation);
    }

    @Operation(summary = "Delete a driveroperation by id", description = "Returns the confirmation string")
    @DeleteMapping("/driveroperations/{driverOps-id}")
    public ResponseEntity<String> deleteDriverOperation(@PathVariable("driverOps-id") int driverOpsId){
        driverOperationService.deleteDriverOperation(driverOpsId);
        return ResponseEntity.ok().body("Driver Operation is deleted");
    }
}
