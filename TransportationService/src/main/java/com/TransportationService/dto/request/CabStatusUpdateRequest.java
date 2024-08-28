package com.TransportationService.dto.request;

import com.TransportationService.entity.CabStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CabStatusUpdateRequest {

    private CabStatus status;
}
