package com.TransportationService.dto.response;

import com.TransportationService.entity.Role;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserResponseDto {

    private int id;

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String phoneNumber;

    private String address;

    private Role role;
}
