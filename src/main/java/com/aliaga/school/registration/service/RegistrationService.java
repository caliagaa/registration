package com.aliaga.school.registration.service;

import com.aliaga.school.registration.config.RegistrationConfig;
import com.aliaga.school.registration.dto.Course;
import com.aliaga.school.registration.dto.Registration;
import com.aliaga.school.registration.dto.Student;
import com.aliaga.school.registration.exception.RegistrationAlreadyExistsException;
import com.aliaga.school.registration.exception.RegistrationMaxAmountStudentsInCourseException;
import com.aliaga.school.registration.exception.RegistrationNotFoundException;
import com.aliaga.school.registration.exception.RegistrationServiceException;
import com.aliaga.school.registration.exception.RegistrationStudentAlreadyInCourseException;
import com.aliaga.school.registration.exception.RegistrationStudentCannotTakeMoreCoursesException;
import com.aliaga.school.registration.exception.StudentServiceException;
import com.aliaga.school.registration.mapper.CourseMapper;
import com.aliaga.school.registration.mapper.RegistrationMapper;
import com.aliaga.school.registration.mapper.StudentMapper;
import com.aliaga.school.registration.repository.CourseRepository;
import com.aliaga.school.registration.repository.RegistrationRepository;
import com.aliaga.school.registration.repository.StudentRepository;
import com.aliaga.school.registration.repository.entity.CourseEntity;
import com.aliaga.school.registration.repository.entity.RegistrationEntity;
import com.aliaga.school.registration.repository.entity.StudentEntity;
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

    public void registerStudentToCourse(Registration registration)
            throws RegistrationServiceException, RegistrationStudentAlreadyInCourseException ,
            RegistrationMaxAmountStudentsInCourseException , RegistrationStudentCannotTakeMoreCoursesException, RegistrationAlreadyExistsException {
        try {
            if (!canStudentRegisterMoreCourses(registration.getStudent().getId())) {
                throw new RegistrationStudentCannotTakeMoreCoursesException("Student cannot register more courses");
            }
            List<RegistrationEntity> registrationEntityList = new ArrayList<>();
            for (Course course: registration.getCourses()) {
                if (isRegistrationAlreadyCreated(registration.getStudent().getId(), course.getId())) {
                    throw new RegistrationAlreadyExistsException("Registration already exists");
                }
                if (!canStudentRegisterInCourse(course.getId())) {
                    throw new RegistrationMaxAmountStudentsInCourseException("Course id: " + course.getId() + " is at maximum capacity, no more students allowed");
                }
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
        } catch (PersistenceException e) {
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

        } catch (PersistenceException e) {
            throw new RegistrationServiceException(e.getMessage());
        }
    }

    public List<Course> getCoursesWithoutStudents() throws RegistrationServiceException {
        try {
            List<CourseEntity> coursesWithoutStudents = courseRepository.getCoursesWithoutStudents();
            return courseMapper.toCourseDTO(coursesWithoutStudents);
        } catch (PersistenceException e) {
            throw new RegistrationServiceException(e.getMessage());
        }
    }

    public List<Student> getStudentsWithoutCourses() throws RegistrationServiceException {
        try {
            List<StudentEntity> coursesWithoutStudents = studentRepository.getStudentsWithoutCourse();
            return studentMapper.toStudentDTO(coursesWithoutStudents);
        } catch (PersistenceException e) {
            throw new RegistrationServiceException(e.getMessage());
        }
    }

    private boolean validateStudentIsAlreadyInCourse(long studentId, long courseId) throws RegistrationServiceException {
        List<Course> coursesByStudent = getCoursesByStudent(studentId);
        return coursesByStudent.stream().anyMatch(f -> f.getId() == courseId);
    }

    private boolean canStudentRegisterInCourse(long courseId) throws RegistrationServiceException {
        int studentsPerCourse = studentsPerCourse(courseId);
        return studentsPerCourse < registrationConfig.getMaxStudentsPerCourse();
    }

    private boolean canStudentRegisterMoreCourses(long studentId) throws RegistrationServiceException {
        int coursesPerStudent = coursesPerStudent(studentId);
        return coursesPerStudent < registrationConfig.getStudentCanRegisterMax();
    }

    private int studentsPerCourse(long courseId) throws RegistrationServiceException {
        try {
            return  Optional.ofNullable(registrationRepository.getStudentCountByCourse(courseId)).orElse(0);
        } catch (PersistenceException e) {
            throw new RegistrationServiceException(e.getMessage());
        }
    }

    private int coursesPerStudent(long studentId) throws RegistrationServiceException {
        try {
            return Optional.ofNullable(registrationRepository.getCoursesCountByStudent(studentId)).orElse(0);
        } catch (PersistenceException e) {
            throw new RegistrationServiceException(e.getMessage());
        }
    }

    private boolean isRegistrationAlreadyCreated(long studentId, long courseId) throws RegistrationServiceException {
        try {
            List<RegistrationEntity> registrationMatches = registrationRepository.findByStudentIdAndCourseId(studentId, courseId);
            if (!registrationMatches.isEmpty()) {
                return true;
            }
        } catch (PersistenceException e) {
            throw new RegistrationServiceException(e.getMessage());
        }
        return false;
    }
}


