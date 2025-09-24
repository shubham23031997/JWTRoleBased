package com.crudOperation.schoolDetails.SecurityConfiguration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {
    // Custom utility class for generating/validating JWTs
    private final JwtUtil jwtConfig;

    public JwtFilter(JwtUtil jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        // 👉 Get the "Authorization" header from request
        String authHeader = request.getHeader("Authorization");

        // 👉 Check if header exists and starts with "Bearer <token>"
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Remove "Bearer " and keep only the token part
            String token = authHeader.substring(7);

            // Extract username from token using JwtUtil
            String username = jwtConfig.extractUsername(token);
            String role = jwtConfig.extractRole(token);

            // 👉 Validate the token
            if (!jwtConfig.validateToken(token, username)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
                return; // stop request if token is invalid
            }

            // 👉 Create authentication object (with username + empty roles)
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

            // 👉 Store authentication in SecurityContext (so Spring Security knows user is logged in)
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } else {
            // 👉 If no token present in request, return 401 Unauthorized
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Token");
            return;
        }

        // 👉 Continue with filter chain (let request reach controller)
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest httpServletRequest) throws ServletException {
        // 👉 This method tells Spring Security NOT to apply JWT filter
        // for certain endpoints (e.g., login endpoint /authenticate).
        String path = httpServletRequest.getServletPath();
        return path.equals("/authenticate");
    }
}



