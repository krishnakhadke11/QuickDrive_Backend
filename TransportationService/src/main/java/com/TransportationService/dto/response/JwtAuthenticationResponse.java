package com.TransportationService.dto.response;

import com.TransportationService.entity.Role;
import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
    private Role role;

}
