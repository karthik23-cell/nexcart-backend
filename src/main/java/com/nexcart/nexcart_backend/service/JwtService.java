package com.nexcart.nexcart_backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkey123456";
    private SecretKey getSignInKey(){

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );
    }
    public String generateToken(String email){

        return Jwts
                .builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60
                        )
                )
                .signWith(
                        getSignInKey(),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token){

        return extractAllClaims(token)
                .getSubject();
    }

    private boolean isTokenExpired(String token){

        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    public boolean isTokenValid(
            String token,
            String email){

        String username =
                extractUsername(token);

        return username.equals(email)
                &&
                !isTokenExpired(token);
    }
}
