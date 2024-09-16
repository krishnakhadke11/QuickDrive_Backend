package com.TransportationService.controller;

import com.TransportationService.dto.request.CabDto;
import com.TransportationService.dto.request.CabUpdateDto;
import com.TransportationService.entity.Cab;
import com.TransportationService.entity.User;
import com.TransportationService.service.CabService;
import com.TransportationService.validation.CabValidation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CabController {

    private CabService cabService;

    private CabValidation cabValidation;

    @Autowired
    public CabController(CabService cabService, CabValidation cabValidation) {
        this.cabService = cabService;
        this.cabValidation = cabValidation;
    }

    @Operation(summary = "Add a cab", description = "Returns newly added cab")
    @PostMapping("/cabs")
    public ResponseEntity<Cab> addCab(@Valid @RequestBody CabDto cabDto) {
        //Validation
        cabValidation.validateCab(cabDto);

        Cab savedCab = cabService.addCab(cabDto);
        return ResponseEntity.ok().body(savedCab);
    }

    @Operation(summary = "Get all the cabs", description = "Returns all the cabs")
    @GetMapping("/cabs")
    public ResponseEntity<List<Cab>> getAllCabs() {
        List<Cab> cabs = cabService.findAllCabs();
        return ResponseEntity.ok().body(cabs);
    }

    @Operation(summary = "Get a cab by id", description = "Returns the cab as per the id")
    @GetMapping("/cabs/{cab-id}")
    public ResponseEntity<Cab> getCabById(@PathVariable("cab-id") int cabId) {
        Cab cab = cabService.findCabById(cabId);
        return ResponseEntity.ok().body(cab);
    }

    @Operation(summary = "Update a cab", description = "Returns the updated cab ")
    @PutMapping("/cabs")
    public ResponseEntity<Cab> updateCab(@Valid @RequestBody CabUpdateDto cabUpdateDto) {
        //Validation
        cabValidation.validateCab(cabUpdateDto);

        Cab updatedCab = cabService.updateCab(cabUpdateDto);
        return ResponseEntity.ok().body(updatedCab);
    }

    @Operation(summary = "Delete a cab by id", description = "Confirmation String")
    @DeleteMapping("/cabs/{cab-id}")
    public ResponseEntity<String> deleteCab(@PathVariable("cab-id") int cabId) {
        cabService.deleteCab(cabId);
        return ResponseEntity.ok().body("Cab has been deleted");
    }

    @Operation(summary = "Get the owner of the cab by cab id", description = "Returns the Owner of a cab")
    @GetMapping("/cab/{cab-id}/owner")
    public ResponseEntity<User> getCabOwner(@PathVariable("cab-id") int cabId) {
        User user = cabService.findCabsOwner(cabId);
        return ResponseEntity.ok().body(user);
    }
}
