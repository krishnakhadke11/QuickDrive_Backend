package com.TransportationService.controller;

import com.TransportationService.entity.Cab;
import com.TransportationService.entity.User;
import com.TransportationService.service.CabService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CabController {

    private CabService cabService;

    public CabController(CabService cabService) {
        this.cabService = cabService;
    }

    @PostMapping("/cab")
    public ResponseEntity<Cab> addCab(@RequestBody Cab cab) {
        Cab savedCab = cabService.addCab(cab);
        return ResponseEntity.ok().body(savedCab);
    }

    @GetMapping("/cab")
    public ResponseEntity<List<Cab>> getAllCabs() {
        List<Cab> cabs = cabService.findAllCabs();
        return ResponseEntity.ok().body(cabs);
    }

    @GetMapping("/cab/{id}")
    public ResponseEntity<Cab> getCabById(@PathVariable int id) {
        Cab cab = cabService.findCabById(id);
        return ResponseEntity.ok().body(cab);
    }

    @PutMapping("/cab")
    public ResponseEntity<Cab> updateCab(@RequestBody Cab cab) {
        Cab updatedCab = cabService.updateCab(cab);
        return ResponseEntity.ok().body(updatedCab);
    }

    @DeleteMapping("/cab/{id}")
    public ResponseEntity<String> deleteCab(@PathVariable int id) {
        cabService.deleteCab(id);
        return ResponseEntity.ok().body("Cab has been deleted");
    }

    @GetMapping("/cab/{cabId}/owner")
    public ResponseEntity<User> getCabOwner(@PathVariable int cabId) {
        User user = cabService.findCabsOwner(cabId);
        return ResponseEntity.ok().body(user);
    }
}
