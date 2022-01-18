package com.medadata.school.registration.controller;

import com.medadata.school.registration.dto.Course;
import com.medadata.school.registration.dto.Student;
import com.medadata.school.registration.exception.CourseNotFoundException;
import com.medadata.school.registration.exception.CourseServiceException;
import com.medadata.school.registration.exception.StudentNotFoundException;
import com.medadata.school.registration.exception.StudentServiceException;
import com.medadata.school.registration.service.CourseService;
import com.medadata.school.registration.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping(value = "")
    public ResponseEntity<Course> createStudent(@RequestBody Course course) throws CourseServiceException {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> readStudent(@PathVariable long id) throws CourseServiceException, CourseNotFoundException {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course, @PathVariable long id)
            throws CourseServiceException, CourseNotFoundException {
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) throws CourseServiceException {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }
}
