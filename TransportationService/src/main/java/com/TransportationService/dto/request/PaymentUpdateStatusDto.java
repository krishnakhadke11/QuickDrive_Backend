package com.TransportationService.dto.request;

import com.TransportationService.entity.PaymentStatus;
import lombok.Data;

@Data
public class PaymentUpdateStatusDto {

    private int paymentId;
    private PaymentStatus paymentStatus;
}