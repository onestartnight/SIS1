package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Size(min = 12, max = 12)
    @Column(unique = true, nullable = false, length = 12)
    private String studentNumber;

    @Pattern(regexp = "\\d{11}")
    @Column(nullable = false, length = 11)
    private String phone;

    private String address;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}