package com.aliaga.school.registration.controller;

import com.aliaga.school.registration.dto.Course;
import com.aliaga.school.registration.exception.CourseAlreadyExistsException;
import com.aliaga.school.registration.exception.CourseNotFoundException;
import com.aliaga.school.registration.exception.CourseServiceException;
import com.aliaga.school.registration.service.CourseService;
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
@RequestMapping(path = "/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping(value = "")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) throws CourseServiceException, CourseAlreadyExistsException {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> readCourse(@PathVariable long id) throws CourseServiceException, CourseNotFoundException {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<Course>> readAllCourses() throws CourseServiceException {
        return ResponseEntity.ok(courseService.getAllCourses());
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
