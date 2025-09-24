package com.crudOperation.schoolDetails.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data//getter,setter,toString,requiredArgsConstructor, equal and hashcode
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
    // private String role;// here we're adding role filed for role based authorization
}