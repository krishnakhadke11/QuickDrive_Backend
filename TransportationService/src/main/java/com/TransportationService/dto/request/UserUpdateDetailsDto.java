package com.TransportationService.dto.request;

import lombok.Data;

@Data
public class UserUpdateDetailsDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
}
