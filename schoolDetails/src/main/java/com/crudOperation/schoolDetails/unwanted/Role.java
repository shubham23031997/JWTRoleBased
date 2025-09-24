package com.crudOperation.schoolDetails.unwanted;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // e.g. "ADMIN" or "USER"
    @Column(nullable = false, unique = true)
    private String name;
}

