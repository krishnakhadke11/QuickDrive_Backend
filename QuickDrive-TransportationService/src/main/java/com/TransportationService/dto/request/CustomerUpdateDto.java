package com.TransportationService.dto.request;

import lombok.Data;

@Data
public class CustomerUpdateDto {
    private int id;
    private UserUpdateDetailsDto user;
}
