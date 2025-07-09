package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> list() {
        return studentService.findAll();
    }

    @GetMapping("/{studentNumber}")
    public Student getByStudentNumber(@PathVariable String studentNumber) {
        Optional<Student> opt = studentService.findByStudentNumber(studentNumber);
        return opt.orElse(null);
    }

    @PostMapping
    public Student createOrUpdate(@RequestBody Student student) {
        return studentService.save(student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        studentService.deleteById(id);
    }
}