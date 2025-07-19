package com.example.backend.service;

import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.exception.UnauthorizedException;
import com.example.backend.modle.Student;
import com.example.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class studentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student, String userId) {
        Student existingStudent = studentRepository.findByEmailAndUserId(student.getEmail(),userId);
        if (existingStudent != null) {
            throw new RuntimeException("Student already exists with id: " + student.getId());
        }
        student.setUserId(userId);
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents(String userId) {
        List <Student> students = studentRepository.findByUserId(userId);
        if(students.isEmpty()) {
            throw new ResourceNotFoundException("No students found for this user");
        }
        return students;
    }

    public Student getStudentById(String userId, String studentId ) {
     Student student= studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        if (!student.getUserId().equals(userId)) {
            throw new UnauthorizedException("Access denied to student record");
        }

        return student;
    }


    public void deleteStudentById(String userId, String studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        if(!student.getUserId().equals(userId)) {
            throw new UnauthorizedException("Access denied to delete student record");
        }

        studentRepository.deleteById(studentId);
    }

    public Student updateStudent(Student updateStudent, String userId) {
        Student existingStudent = studentRepository.findById(updateStudent.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + updateStudent.getId()));
        if (!existingStudent.getUserId().equals(updateStudent.getUserId())) {
            throw new UnauthorizedException("Access denied to update student record");
        }

        existingStudent.setName(updateStudent.getName());
        existingStudent.setEmail(updateStudent.getEmail());
        existingStudent.setPhone(updateStudent.getPhone());
        existingStudent.setAddress(updateStudent.getAddress());
        existingStudent.setAge(updateStudent.getAge());
        existingStudent.setPhone(updateStudent.getPhone());


        return studentRepository.save(existingStudent);
    }
}
