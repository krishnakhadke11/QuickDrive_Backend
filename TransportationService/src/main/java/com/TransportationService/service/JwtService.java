package com.TransportationService.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

public interface JwtService {

    String extractUsername(String token);

    String generateToken(UserDetails userDetails, Integer id);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefrestToken(HashMap<String, Object> extraClaims, UserDetails userDetails,Integer id);

    Integer getIdFromToken(String token);
}
