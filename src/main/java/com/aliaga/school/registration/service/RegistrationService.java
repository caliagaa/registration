package com.aliaga.school.registration.service;

import com.aliaga.school.registration.config.RegistrationConfig;
import com.aliaga.school.registration.dto.Course;
import com.aliaga.school.registration.dto.Registration;
import com.aliaga.school.registration.dto.Student;
import com.aliaga.school.registration.exception.RegistrationNotFoundException;
import com.aliaga.school.registration.exception.RegistrationServiceException;
import com.aliaga.school.registration.exception.RegistrationStudentAlreadyInCourseException;
import com.aliaga.school.registration.mapper.CourseMapper;
import com.aliaga.school.registration.mapper.RegistrationMapper;
import com.aliaga.school.registration.mapper.StudentMapper;
import com.aliaga.school.registration.repository.CourseRepository;
import com.aliaga.school.registration.repository.RegistrationRepository;
import com.aliaga.school.registration.repository.StudentRepository;
import com.aliaga.school.registration.repository.entity.CourseEntity;
import com.aliaga.school.registration.repository.entity.RegistrationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RegistrationService {

    private final RegistrationConfig registrationConfig;

    private final RegistrationRepository registrationRepository;

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    private RegistrationMapper registrationMapper;

    private CourseMapper courseMapper;

    private StudentMapper studentMapper;

    public RegistrationService(RegistrationConfig registrationConfig,
                               RegistrationRepository registrationRepository,
                               StudentRepository studentRepository,
                               CourseRepository courseRepository,
                               RegistrationMapper registrationMapper,
                               CourseMapper courseMapper,
                               StudentMapper studentMapper) {
        this.registrationConfig = registrationConfig;
        this.registrationRepository = registrationRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.registrationMapper = registrationMapper;
        this.courseMapper = courseMapper;
        this.studentMapper = studentMapper;
    }

    public void registerStudentToCourse(Registration registration) throws RegistrationServiceException, RegistrationStudentAlreadyInCourseException {
        try {
            List<RegistrationEntity> registrationEntityList = new ArrayList<>();
            for (Course course: registration.getCourses()) {
                if (!validateStudentIsAlreadyInCourse(registration.getStudent().getId(), course.getId())) {
                    registrationEntityList.add(RegistrationEntity.builder()
                            .student(studentMapper.toStudentEntity(registration.getStudent()))
                            .course(courseMapper.toCourseEntity(course))
                            .build());
                } else {
                    log.warn("Student already enrolled in course");
                    throw new RegistrationStudentAlreadyInCourseException("Student already enrolled in course");
                }
            }
            registrationRepository.saveAll(registrationEntityList);
        } catch (PersistenceException e) {
            throw new RegistrationServiceException(e.getMessage());
        }
    }

    public Registration getRegistrationById(long id) throws RegistrationServiceException, RegistrationNotFoundException {
        try {
            Optional<RegistrationEntity> registrationEntity = registrationRepository.findById(id);
            if (registrationEntity.isPresent()) {
                return registrationMapper.toRegistrationDTO(registrationEntity.get());
            } else {
                throw new RegistrationNotFoundException("Registration not found");
            }
        } catch (PersistenceException e) {
            throw new RegistrationServiceException(e.getMessage());
        }
    }

    public List<Registration> getAllRegistrations() throws RegistrationServiceException {
        try {
            List<RegistrationEntity> registrationEntities = registrationRepository.findAll();
            return registrationMapper.toRegistrationDTO(registrationEntities);
        } catch (PersistenceException e) {
            throw new RegistrationServiceException(e.getMessage());
        }
    }

    public List<Student> getStudentsByCourse(long courseId) throws RegistrationServiceException {
        try {
            List<RegistrationEntity> studentsByCourse = registrationRepository.getStudentsByCourseId(courseId);
            List<Registration> registrations = registrationMapper.toRegistrationDTO(studentsByCourse);
            return registrations
                    .stream()
                    .map(Registration::getStudent)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RegistrationServiceException(e.getMessage());
        }
    }

    public List<Course> getCoursesByStudent(long studentId) throws RegistrationServiceException {
        try {
            List<RegistrationEntity> coursesByStudent = registrationRepository.getCoursesByStudentId(studentId);
            List<CourseEntity> courseEntities = coursesByStudent
                    .stream()
                    .map(RegistrationEntity::getCourse)
                    .collect(Collectors.toList());
            return courseMapper.toCourseDTO(courseEntities);

        } catch (Exception e) {
            throw new RegistrationServiceException(e.getMessage());
        }
    }

    public List<Course> getCoursesWithoutStudents() throws RegistrationServiceException {
        try {
            List<CourseEntity> coursesWithoutStudents = courseRepository.getCoursesWithoutStudents();
            return courseMapper.toCourseDTO(coursesWithoutStudents);
        } catch (Exception e) {
            throw new RegistrationServiceException(e.getMessage());
        }
    }

    private boolean validateStudentIsAlreadyInCourse(long studentId, long courseId) throws RegistrationServiceException {
        List<Course> coursesByStudent = getCoursesByStudent(studentId);
        return coursesByStudent.stream().anyMatch(f -> f.getId() == courseId);
    }

}
