package com.TransportationService.dto.request;

import com.TransportationService.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDetailsDto {
    @Pattern(regexp = "^[A-za-z]+$",message = "Please enter valid firstname")
    private String firstName;

    @Pattern(regexp = "^[A-za-z]+$" ,message = "Please enter valid lastname")
    private String lastName;

    @Email
    private String email;

    private String password;

    @Size(min = 10, max = 10)
    @Pattern(regexp = "^[0-9]+$")
    private String phoneNumber;

    private String address;
}
