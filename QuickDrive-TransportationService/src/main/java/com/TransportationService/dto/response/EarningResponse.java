package com.TransportationService.dto.response;

import lombok.Data;

@Data
public class EarningResponse {

    private Double total;

    private Double cash;

    private Double online;

    private Double cashPercentage;

    private Double onlinePercentage;

}
