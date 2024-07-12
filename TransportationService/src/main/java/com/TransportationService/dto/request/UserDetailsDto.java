package com.TransportationService.dto.request;

import com.TransportationService.entity.Role;
import lombok.Data;

@Data
public class UserDetailsDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
}
