package com.TransportationService.dto.request;

import com.TransportationService.entity.PaymentStatus;
import com.TransportationService.entity.PaymentType;
import lombok.Data;

@Data
public class PaymentDto {
    private PaymentType paymentType;

    private PaymentStatus paymentStatus;

    RideIdDto ride;
}
