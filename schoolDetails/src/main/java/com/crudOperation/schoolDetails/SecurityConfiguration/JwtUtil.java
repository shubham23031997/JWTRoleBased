package com.crudOperation.schoolDetails.SecurityConfiguration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private String secret = "mySecretKey"; // secret key
   /* private static final Map<String, String> USERS = Map.of(
            "admin", "password",
            "amar", "1234"
    );

    private static final Map<String, String> ROLES = Map.of(
            "admin", "ADMIN",
            "amar", "USER"
    );*/


    // 1. Generate JWT token: need to pass username, token expiry time, algorithm
    public String generateToken(String username, String password, String role) {
        return Jwts.builder()
                .setSubject(username)// set username in token
                .setSubject(password)// set password in token
                .claim("role", role) // set role in token
                .setIssuedAt(new Date()) // issue time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // expires in 1 hour
                .signWith(SignatureAlgorithm.HS256, secret) // sign with secret key
                .compact();
    }

    // Extract username from token
    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody().getSubject();
    }

    //extract role
    public String extractRole(String token) {
        return (String) Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("role");
    }

    //2. Validate token with help of username and secret key
    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

}