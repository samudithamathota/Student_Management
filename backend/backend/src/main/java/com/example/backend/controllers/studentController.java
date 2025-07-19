package com.example.backend.controllers;

import com.example.backend.modle.Student;
import com.example.backend.service.studentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class studentController {

    @Autowired
    private studentService studentService;

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(HttpServletRequest request, @RequestBody Student student) {
        String userId = (String) request.getAttribute("userId");
        Student created = studentService.createStudent(student, userId);
        return ResponseEntity.ok(created);
    }


    @GetMapping("/all")
    public List<Student> getAllStudents(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return studentService.getAllStudents(userId);
    }

    @GetMapping("/get/{id}")
    public Student getStudentById(@PathVariable String id, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return studentService.getStudentById(userId, id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student updatestudent, HttpServletRequest request) {
        updatestudent.setId(id);
        updatestudent.setUserId((String) request.getAttribute("userId"));
        String userId = (String) request.getAttribute("userId");

        Student student = studentService.updateStudent(updatestudent, userId);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable String id, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        studentService.deleteStudentById(userId, id);
        return ResponseEntity.noContent().build();



    }
}