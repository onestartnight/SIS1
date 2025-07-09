package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Size(min = 12, max = 12)
    @Column(unique = true)
    private String studentNumber;

    @Pattern(regexp = "\\d{11}")
    private String phone;

    private String address;

    @Email
    @Column(unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    // getter/setter省略
}