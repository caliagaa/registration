package com.aliaga.school.registration.controller;

import com.aliaga.school.registration.dto.Course;
import com.aliaga.school.registration.dto.Registration;
import com.aliaga.school.registration.dto.Student;
import com.aliaga.school.registration.exception.RegistrationMaxAmountStudentsInCourseException;
import com.aliaga.school.registration.exception.RegistrationNotFoundException;
import com.aliaga.school.registration.exception.RegistrationServiceException;
import com.aliaga.school.registration.exception.RegistrationStudentAlreadyInCourseException;
import com.aliaga.school.registration.exception.RegistrationStudentCannotTakeMoreCoursesException;
import com.aliaga.school.registration.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/registration-courses")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(value = "")
    public ResponseEntity<Void> registerStudentToCourses(@RequestBody Registration registration) throws RegistrationServiceException,
            RegistrationStudentAlreadyInCourseException,
            RegistrationMaxAmountStudentsInCourseException,
            RegistrationStudentCannotTakeMoreCoursesException {
        registrationService.registerStudentToCourse(registration);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Registration> readRegistration(@PathVariable long id) throws RegistrationServiceException, RegistrationNotFoundException {
        return ResponseEntity.ok(registrationService.getRegistrationById(id));
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Registration>> readAllRegistrations() throws RegistrationServiceException {
        return ResponseEntity.ok(registrationService.getAllRegistrations());
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getStudentsByCourse(@RequestParam(name = "course-id") long courseId) throws RegistrationServiceException, RegistrationNotFoundException {
        return ResponseEntity.ok(registrationService.getStudentsByCourse(courseId));
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getCoursesByStudent(@RequestParam(name = "student-id") long studentId) throws RegistrationServiceException, RegistrationNotFoundException {
        return ResponseEntity.ok(registrationService.getCoursesByStudent(studentId));
    }

    @GetMapping("/courses/without-students")
    public ResponseEntity<List<Course>> getCoursesWithoutStudents() throws RegistrationServiceException {
        return ResponseEntity.ok(registrationService.getCoursesWithoutStudents());
    }

    @GetMapping("/students/without-courses")
    public ResponseEntity<List<Student>> getStudentsWithoutCourses() throws RegistrationServiceException {
        return ResponseEntity.ok(registrationService.getStudentsWithoutCourses());
    }
}
