package com.TransportationService.dto.request;

import com.TransportationService.entity.CabStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalTime;

@Data
public class DriverOperationUpdateDto {
    private int id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSS")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSS")
    private LocalTime endTime;

    private CabStatus status = CabStatus.AVAILABLE;

    private DriverIdDto driver;

    private CabIdDto cab;
}
