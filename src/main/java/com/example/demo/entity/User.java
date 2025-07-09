package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // 学号或管理员名

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // "student" or "admin"
}