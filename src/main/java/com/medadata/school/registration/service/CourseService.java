package com.medadata.school.registration.service;

import com.medadata.school.registration.dto.Course;
import com.medadata.school.registration.exception.CourseNotFoundException;
import com.medadata.school.registration.exception.CourseServiceException;
import com.medadata.school.registration.exception.StudentNotFoundException;
import com.medadata.school.registration.mapper.CourseMapper;
import com.medadata.school.registration.repository.CourseRepository;
import com.medadata.school.registration.repository.entity.CourseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.Optional;

@Service
public class CourseService {

    private static final String NOT_FOUND = "Course not found";

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public Course createCourse(Course course) throws CourseServiceException {
        try {
            CourseEntity newCourse = courseRepository.save(courseMapper.toCourseEntity(course));
            return courseMapper.toCourseDTO(newCourse);
        } catch (PersistenceException e) {
            throw new CourseServiceException(e.getMessage());
        }
    }

    public Course getCourseById(long id) throws CourseNotFoundException, CourseServiceException {
        try {
            Optional<CourseEntity> course = courseRepository.findById(id);
            if (course.isPresent()) {
                return courseMapper.toCourseDTO(course.get());
            } else {
                throw new CourseNotFoundException(NOT_FOUND);
            }
        } catch (PersistenceException e) {
            throw new CourseServiceException(e.getMessage());
        }
    }

    public Course updateCourse(long id, Course course) throws CourseServiceException, CourseNotFoundException {
        try {
            Optional<CourseEntity> courseEntity = courseRepository.findById(id);
            if (courseEntity.isPresent()) {
                CourseEntity courseToUpdate = courseMapper.toCourseEntity(course);
                return courseMapper.toCourseDTO(courseRepository.save(courseToUpdate));
            } else {
                throw new CourseNotFoundException(NOT_FOUND);
            }
        } catch (PersistenceException e) {
            throw new CourseServiceException(e.getMessage());
        }
    }

    public void deleteCourse(long id) throws CourseServiceException {
        try {
            Optional<CourseEntity> courseEntity = courseRepository.findById(id);
            courseEntity.ifPresent(courseRepository::delete);
        } catch (PersistenceException e) {
            throw new CourseServiceException(e.getMessage());
        }
    }
}
