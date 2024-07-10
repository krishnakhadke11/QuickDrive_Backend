package com.TransportationService.service.impl;

import com.TransportationService.dto.response.FarePriceResponseDto;
import com.TransportationService.entity.FarePrice;
import com.TransportationService.service.FarePriceService;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.rmi.UnexpectedException;

@Service
public class FarePriceServiceImpl implements FarePriceService {

    @Value("${google.maps.API_KEY}")
    String API_KEY;

    public String[] getDistance(String[] src,String[] dest) throws IOException, InterruptedException, ApiException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        DistanceMatrix distanceMatrix = DistanceMatrixApi.getDistanceMatrix(context, src, dest)
                .mode(TravelMode.DRIVING)
                .await();

        String distance = distanceMatrix.rows[0].elements[0].distance.humanReadable;
        String duration = distanceMatrix.rows[0].elements[0].duration.humanReadable;

        System.out.println("Distance: " + distanceMatrix.rows[0].elements[0].distance.humanReadable);
        System.out.println("Duration: " + distanceMatrix.rows[0].elements[0].duration.humanReadable);

        return new String[]{distance, duration};
    }

    @Override
    public FarePriceResponseDto checkFarePrice(String pickupLocation, String dropLocation, int seatingCapacity) throws IOException, InterruptedException, ApiException {
        String[] src = {pickupLocation}; // Example: New York
        String[] dest = {dropLocation}; // Example: Los Angeles

        String[] elements;
        if(src[0].isEmpty() || dest[0].isEmpty()){
            throw new UnexpectedException("Either Pickup or Drop location is empty");
        }else{
            elements = getDistance(src, dest);
        }
        elements[0] = elements[0].replaceAll("[^\\d.]","");
        double distance = Double.parseDouble(elements[0]);
        double fare = 0;
        if(seatingCapacity == 5){
            fare += (distance * FarePrice.FIVE_SEATER.getRatePerKm());
        }else if(seatingCapacity == 7){
            fare += (distance * FarePrice.SEVEN_SEATER.getRatePerKm());
        }

        FarePriceResponseDto farePriceResponseDto = new FarePriceResponseDto();
        farePriceResponseDto.setPickupLocation(src[0]);
        farePriceResponseDto.setDropLocation(dest[0]);
        farePriceResponseDto.setSeatingCapacity(seatingCapacity);
        farePriceResponseDto.setDistance(elements[0]+" Km");
        farePriceResponseDto.setDuration(elements[1]);
        farePriceResponseDto.setFare((int)fare);

        return farePriceResponseDto;
    }
}
