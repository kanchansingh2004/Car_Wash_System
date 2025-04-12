package com.authentication_service.authservice.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {
    // Secret key used for signing the JWT
    private static final String SECRET = "secret_key_for_jwt_generation_123456789";

    //Token validity time
    private static final long EXPIRE_TIME = 60 * 60 * 1000;

    //Generate signing key using HMAC-SHA256
    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String email, String role, String firstName, String lastName) {
        return Jwts.builder()
                .setSubject(email)  // email as subject
                .addClaims(Map.of(
                        "role", role,
                        "firstName", firstName,
                        "lastName", lastName
                ))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }



    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Parse and extract all claims from the token using the secret key
    private Claims extractAllClaims (String token){
        return Jwts.parserBuilder().setSigningKey(getSigningKey())
                .build().parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, String email){
        final String extractedEmail = extractEmail((token));
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    // Check if token is expired
    public boolean isTokenExpired(String token) {
        try {
            return extractAllClaims(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean extractIssuedAt(String token) {
        return true;
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public String extractFirstName(String token){
        return extractAllClaims(token).get("firstName", String.class);
    }

    public String extractLastName(String token){
        return extractAllClaims(token).get("lastName", String.class);
    }


}

