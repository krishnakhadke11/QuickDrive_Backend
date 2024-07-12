package com.TransportationService.dto.request;

import lombok.Data;

import java.time.LocalTime;

@Data
public class DriverOperationUpdateDto {
    private int id;

    private LocalTime startTime;

    private LocalTime endTime;

    private DriverIdDto driver;

    private CabIdDto cab;
}
