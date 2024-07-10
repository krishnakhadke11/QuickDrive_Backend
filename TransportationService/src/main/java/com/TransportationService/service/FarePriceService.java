package com.TransportationService.service;

import com.TransportationService.dto.response.FarePriceResponseDto;
import com.google.maps.errors.ApiException;

import java.io.IOException;

public interface FarePriceService {

    FarePriceResponseDto checkFarePrice(String pickupLocation, String dropLocation, int seatingCapacity) throws IOException, InterruptedException, ApiException;
}
