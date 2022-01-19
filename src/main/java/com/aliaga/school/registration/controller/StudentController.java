package com.aliaga.school.registration.controller;

import com.aliaga.school.registration.dto.Student;
import com.aliaga.school.registration.exception.StudentAlreadyExistsException;
import com.aliaga.school.registration.exception.StudentNotFoundException;
import com.aliaga.school.registration.exception.StudentServiceException;
import com.aliaga.school.registration.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(value = "")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) throws StudentServiceException, StudentAlreadyExistsException {
         return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> readStudent(@PathVariable long id) throws StudentNotFoundException {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<Student>> readAllStudents() throws StudentServiceException {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable long id)
            throws StudentNotFoundException, StudentServiceException {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) throws StudentServiceException {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
