package com.TransportationService.dto.request;

import com.TransportationService.entity.BookingStatus;
import lombok.Data;

@Data
public class BookingStatusUpdateRequest {

    BookingStatus bookingStatus;
}
