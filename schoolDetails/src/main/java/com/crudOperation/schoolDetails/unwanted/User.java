package com.crudOperation.schoolDetails.unwanted;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    // 👉 role can be ADMIN, USER etc.
    private String role;
}
