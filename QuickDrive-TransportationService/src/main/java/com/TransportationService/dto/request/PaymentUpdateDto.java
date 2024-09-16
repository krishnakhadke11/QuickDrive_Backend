package com.TransportationService.dto.request;

import com.TransportationService.entity.PaymentStatus;
import com.TransportationService.entity.PaymentType;
import lombok.Data;

@Data
public class PaymentUpdateDto {
    private int id;

    private PaymentType paymentType;

    private PaymentStatus paymentStatus;

    RideIdDto ride;
}
