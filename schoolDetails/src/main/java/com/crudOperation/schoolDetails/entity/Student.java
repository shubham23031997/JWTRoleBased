package com.crudOperation.schoolDetails.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name = "student")


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rollNo;

    @Column(name = "student_name")
    private String name;

    @Column(name = "student_percentage")
    private Double percentage;

    @Column(name = "student_branch")
    private String branch;

}
