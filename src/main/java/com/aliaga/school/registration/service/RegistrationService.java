package com.aliaga.school.registration.service;

import com.aliaga.school.registration.dto.Course;
import com.aliaga.school.registration.dto.Student;
import com.aliaga.school.registration.exception.RegistrationNotFoundException;
import com.aliaga.school.registration.mapper.CourseMapper;
import com.aliaga.school.registration.mapper.RegistrationMapper;
import com.aliaga.school.registration.repository.StudentRepository;
import com.aliaga.school.registration.config.RegistrationConfig;
import com.aliaga.school.registration.dto.Registration;
import com.aliaga.school.registration.exception.RegistrationServiceException;
import com.aliaga.school.registration.repository.RegistrationRepository;
import com.aliaga.school.registration.repository.entity.CourseEntity;
import com.aliaga.school.registration.repository.entity.RegistrationEntity;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    private final RegistrationConfig registrationConfig;

    private final RegistrationRepository registrationRepository;

    private final StudentRepository studentRepository;

    private RegistrationMapper registrationMapper;

    private CourseMapper courseMapper;

    public RegistrationService(RegistrationConfig registrationConfig,
                               RegistrationRepository registrationRepository,
                               StudentRepository studentRepository,
                               RegistrationMapper registrationMapper,
                               CourseMapper courseMapper) {
        this.registrationConfig = registrationConfig;
        this.registrationRepository = registrationRepository;
        this.studentRepository = studentRepository;
        this.registrationMapper = registrationMapper;
        this.courseMapper = courseMapper;
    }

    public Registration registerStudentToCourse(Registration registration) throws RegistrationServiceException {
        RegistrationEntity registrationEntity = registrationRepository.save(registrationMapper.toRegistrationEntity(registration));
        return registrationMapper.toRegistrationDTO(registrationEntity);
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
                    .map(c -> c.getCourse())
                    .collect(Collectors.toList());
            return courseMapper.toCourseDTO(courseEntities);

        } catch (Exception e) {
            throw new RegistrationServiceException(e.getMessage());
        }
    }

    private void get() {
        
    }
}
