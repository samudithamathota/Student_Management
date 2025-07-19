package com.example.backend.repository;

import com.example.backend.modle.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {
    List<Student> findByUserId(String userId);
    Student findByEmailAndUserId(String email, String userId);
}
