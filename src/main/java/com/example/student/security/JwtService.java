package com.example.student.security;

import com.example.student.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long expiration;

    public JwtService(JwtProperties jwtProperties) {

        this.signingKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(
                        jwtProperties.secret()
                )
        );

        this.expiration = jwtProperties.expiration();
    }

    public String generateToken(
            String username,
            String role
    ) {

        Date now = new Date();

        Date expiryDate = new Date(
                now.getTime() + expiration
        );

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(signingKey)
                .compact();
    }

    public String extractUsername(String token) {

        return getClaims(token)
                .getSubject();
    }

    public String extractRole(String token) {

        return getClaims(token)
                .get("role", String.class);
    }

    public boolean isTokenValid(String token) {

        try {

            getClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    private Claims getClaims(String token) {  //don't want other classes directly calling this internal parsing method.

        return Jwts.parser()                        //Create a JWT parser
                .verifyWith(signingKey)           //configure it with my signing key
                .build()                          //build the parser
                .parseSignedClaims(token)        //parse and verify the signed JWT
                .getPayload();                   //return its payload/claims.
    }
}