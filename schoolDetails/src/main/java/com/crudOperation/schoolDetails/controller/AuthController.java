package com.crudOperation.schoolDetails.controller;

import com.crudOperation.schoolDetails.SecurityConfiguration.JwtUtil;
import com.crudOperation.schoolDetails.dto.AuthRequest;
import com.crudOperation.schoolDetails.dto.TokenResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
//@RequestMapping("/secure")
public class AuthController {
    private static final Map<String, String> USERS = Map.of(
            "admin", "password",
            "shubham", "1234",
            "ram", "5678"
    );

    // username -> role
    private static final Map<String, String> ROLES = Map.of(
            "admin", "ADMIN",
            "shubham", "USER",
            "ram", "USER"
    );
    public final JwtUtil jwtConfig;

    public AuthController(JwtUtil jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    // Endpoint: http://localhost:8080/authenticate
    @PostMapping("/authenticate")
    public TokenResponse generateToken(@RequestBody AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();


        //  Validate user (here hardcoded, later connect with DB/service)
        //if ("admin".equals(authRequest.getUsername()) && "password".equals(authRequest.getPassword())) {
        if (USERS.containsKey(username) && USERS.get(username).equals(password)) {

            String role = ROLES.get(username);

            // Generate token with role
            String token = jwtConfig.generateToken(username, password, role);//here added role
            return new TokenResponse(token, "Authentication successful");
        } else {
            return new TokenResponse(null, "Invalid username or password!");
        }
    }
}
