package com.TransportationService.controller;

import com.TransportationService.dto.response.FarePriceResponseDto;
import com.TransportationService.service.FarePriceService;
import com.google.maps.errors.ApiException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FarePriceController {

    private FarePriceService farePriceService;

    @Autowired
    public FarePriceController(FarePriceService farePriceService) {
        this.farePriceService = farePriceService;
    }

    @Operation(summary = "Checks the Fare the route", description = "Returns the FarePriceResponseDto")
    @GetMapping("/fare")
    public ResponseEntity<FarePriceResponseDto> getFare(
            @RequestParam(value = "pickupLocation") String pickupLocation,
            @RequestParam(value = "dropLocation") String dropLocation,
            @RequestParam(value = "seatingCapacity") int seatingCapacity
    ) throws IOException, InterruptedException, ApiException {
        FarePriceResponseDto farePriceResponseDto = farePriceService.checkFarePrice(pickupLocation,dropLocation,seatingCapacity);
        return ResponseEntity.ok(farePriceResponseDto);
    }

}
