package com.TransportationService.controller;

import com.TransportationService.dto.request.BookingStatusUpdateRequest;
import com.TransportationService.dto.request.RideIdDto;
import com.TransportationService.dto.request.RideRequestDto;
import com.TransportationService.dto.request.RideRequestUpdateDto;
import com.TransportationService.dto.response.RideRequestResponseDto;
import com.TransportationService.dto.response.RideRequestWithRideDto;
import com.TransportationService.entity.Ride;
import com.TransportationService.entity.RideRequest;
import com.TransportationService.service.RideRequestService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RideRequestController {

    private RideRequestService rideRequestService;

    public RideRequestController(RideRequestService rideRequestService) {
        this.rideRequestService = rideRequestService;
    }

    @Operation(summary = "Create Ride Request", description = "Creating the Ride Request")
    @PostMapping("/riderequests")
    public ResponseEntity<RideRequest> createRideRequest(@RequestBody RideRequestDto rideRequest, HttpServletRequest req) {

        Integer customerId = (Integer)req.getAttribute("id");
        RideRequest rideReq = rideRequestService.addRideRequest(rideRequest,customerId);
        return ResponseEntity.ok(rideReq);
    }
    // /accept request and create ride
//    @Operation(summary = "Accept Ride Request", description = "Accepting the Ride Request")
//    @GetMapping("/riderequest/driver/accept/{id}")
//    public Object acceptRideRequest(@PathVariable int id, HttpServletRequest req) {
//        //Only Driver can accept the ride :
//        Integer driverId = (Integer)req.getAttribute("id");
//        Ride ride = rideRequestService.acceptRideRequest(id,driverId);
//        if(ride != null){
//            return ResponseEntity.ok(ride);
//        }else{
//            return ResponseEntity.internalServerError();
//        }
//    }

    @Operation(summary = "Get Ride Request By Id", description = "Getting the Ride Request By Id")
    @GetMapping("/riderequests/{riderequest-id}")
    public ResponseEntity<RideRequestResponseDto> getRideRequestById(@PathVariable("riderequest-id") int rideReqId){
        RideRequestResponseDto rideRequest = rideRequestService.getRideRequestById(rideReqId);
        return  ResponseEntity.ok(rideRequest);
    }

    @Operation(summary = "Get All Ride Request", description = "Getting All Ride Request")
    @GetMapping("/riderequests")
    public ResponseEntity<List<RideRequest>> getAllRideRequest(){
        List<RideRequest> rideRequest = rideRequestService.getAllRideRequest();
        return  ResponseEntity.ok(rideRequest);
    }


    @Operation(summary = "Get  RideRequest with Ride", description = "Getting Ride Request with ride details")
    @GetMapping("/riderequests/{riderequest-id}/rides")
    public ResponseEntity<RideRequestWithRideDto> getRideFromRideReqId(@PathVariable("riderequest-id") int rideReqId){
        RideRequestWithRideDto rideRequest = rideRequestService.findByIdWithRide(rideReqId);
        return  ResponseEntity.ok(rideRequest);
    }

    @Operation(summary = "Update Ride Request", description = "Returns Ride Request")
    @PutMapping("/riderequests")
    public ResponseEntity<RideRequest> updateRideRequest(RideRequestUpdateDto rideRequestUpdateDto){
        RideRequest rideRequest = rideRequestService.updateRideRequest(rideRequestUpdateDto);
        return  ResponseEntity.ok(rideRequest);
    }

    @Operation(summary = "Update Ride Request", description = "Returns Ride Request")
    @PatchMapping("/riderequests/{riderequest-id}/rides")
    public ResponseEntity<RideRequest> updateRideRequest(@PathVariable("riderequest-id") int rideReqId,@RequestBody RideIdDto rideIdDto){
        RideRequest rideRequest = rideRequestService.patchRideOfRideRequest(rideReqId,rideIdDto);
        return  ResponseEntity.ok(rideRequest);
    }

    @Operation(summary = "Update Ride Request", description = "Returns Ride Request")
    @PatchMapping("/riderequests/{riderequest-id}/booking-status")
    public ResponseEntity<RideRequest> updateRideRequest(@PathVariable("riderequest-id") int rideReqId,@RequestBody BookingStatusUpdateRequest bookingStatusUpdateRequest){
        RideRequest rideRequest = rideRequestService.patchBookingStatusOfRideRequest(rideReqId,bookingStatusUpdateRequest);
        return  ResponseEntity.ok(rideRequest);
    }

    @Operation(summary = "Delete Ride Request By ID", description = "Returns Confirmation String")
    @DeleteMapping("/riderequests/{riderequest-id}")
    public ResponseEntity<String> deleteRideRequest(@PathVariable("riderequest-id") int rideReqId){
        String msg = rideRequestService.deleteRideRequest(rideReqId);
        return  ResponseEntity.ok(msg);
    }
}
