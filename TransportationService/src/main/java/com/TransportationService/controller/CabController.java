package com.TransportationService.controller;

import com.TransportationService.entity.Cab;
import com.TransportationService.entity.User;
import com.TransportationService.service.CabService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CabController {

    private CabService cabService;

    public CabController(CabService cabService) {
        this.cabService = cabService;
    }

    @Operation(summary = "Add a cab", description = "Returns newly added cab")
    @PostMapping("/cab")
    public ResponseEntity<Cab> addCab(@RequestBody Cab cab) {
        Cab savedCab = cabService.addCab(cab);
        return ResponseEntity.ok().body(savedCab);
    }

    @Operation(summary = "Get all the cabs", description = "Returns all the cabs")
    @GetMapping("/cab")
    public ResponseEntity<List<Cab>> getAllCabs() {
        List<Cab> cabs = cabService.findAllCabs();
        return ResponseEntity.ok().body(cabs);
    }

    @Operation(summary = "Get a cab by id", description = "Returns the cab as per the id")
    @GetMapping("/cab/{id}")
    public ResponseEntity<Cab> getCabById(@PathVariable int id) {
        Cab cab = cabService.findCabById(id);
        return ResponseEntity.ok().body(cab);
    }

    @Operation(summary = "Update a cab", description = "Returns the updated cab ")
    @PutMapping("/cab")
    public ResponseEntity<Cab> updateCab(@RequestBody Cab cab) {
        Cab updatedCab = cabService.updateCab(cab);
        return ResponseEntity.ok().body(updatedCab);
    }

    @Operation(summary = "Delete a cab by id", description = "Confirmation String")
    @DeleteMapping("/cab/{id}")
    public ResponseEntity<String> deleteCab(@PathVariable int id) {
        cabService.deleteCab(id);
        return ResponseEntity.ok().body("Cab has been deleted");
    }

    @Operation(summary = "Get the owner of the cab by cab id", description = "Returns the Owner of a cab")
    @GetMapping("/cab/{id}/owner")
    public ResponseEntity<User> getCabOwner(@PathVariable int id) {
        User user = cabService.findCabsOwner(id);
        return ResponseEntity.ok().body(user);
    }
}
