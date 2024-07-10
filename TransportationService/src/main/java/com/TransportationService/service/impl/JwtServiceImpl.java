package com.TransportationService.service.impl;

import com.TransportationService.entity.Role;
import com.TransportationService.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    //Get the secretKey from application.properties
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    //Get the expiration-time from application.properties
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Value("${security.jwt.refrestToken.expiration-time}")
    private long refreshTokenExpirationTime;

    //To extract Username from token we extract claims from the token
    @Override
    public String extractUsername(String token) {
        // getSubject specifies what claims to be extracted
        //Here getSubject means user
        //Where as if we write getExpiration then it will extract expiration time
        //But we want to extract username then we use getSubject
        return extractClaim(token, Claims::getSubject);
    }
    @Override
    public Integer getIdFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("id", Integer.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        //We extracting all the claims from the token
        final Claims claims = extractAllClaims(token);

        //T is used to for flexible datatypes
        // claimsResolver.apply(claims) - THIS is basically used to extract subject as in parameter subject is passed
        // if expiration then expiration is getting applied
        T result = claimsResolver.apply(claims);
//        System.out.println("In JwtServiceImpl.extractClaim" + result);
        return result;
    }

    //Token is being generated
    @Override
    public String generateToken(UserDetails userDetails, Integer id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",id);
        return generateToken(claims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims) // To use custom claims then this is used, That is why we pass Empty Map
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //Check if token is valid
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    @Override
    public String generateRefrestToken(HashMap<String, Object> extraClaims, UserDetails userDetails,Integer id) {
        extraClaims.put("id",id);
        return Jwts
                .builder()
                .setClaims(extraClaims) // To use custom claims then this is used, That is why we pass Empty Map
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
