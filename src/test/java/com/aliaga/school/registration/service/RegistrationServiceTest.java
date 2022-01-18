package com.aliaga.school.registration.service;

import com.aliaga.school.registration.config.RegistrationConfig;
import com.aliaga.school.registration.dto.Course;
import com.aliaga.school.registration.dto.Registration;
import com.aliaga.school.registration.dto.Student;
import com.aliaga.school.registration.exception.RegistrationStudentAlreadyInCourseException;
import com.aliaga.school.registration.mapper.CourseMapper;
import com.aliaga.school.registration.mapper.RegistrationMapper;
import com.aliaga.school.registration.mapper.StudentMapper;
import com.aliaga.school.registration.repository.CourseRepository;
import com.aliaga.school.registration.repository.RegistrationRepository;
import com.aliaga.school.registration.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Spy
    @InjectMocks
    RegistrationService registrationService;

    @Mock
    RegistrationConfig registrationConfig;

    @Mock
    RegistrationRepository registrationRepository;

    @Mock
    StudentRepository studentRepository;

    @Mock
    CourseRepository courseRepository;

    @Mock
    RegistrationMapper registrationMapper;

    @Mock
    CourseMapper courseMapper;

    @Mock
    StudentMapper studentMapper;

    @Test
    void testRegisterStudentToCourse_alreadyEnrolled() {
        Course course = Course.builder().id(1).name("How to make money").build();
        Registration registration = Registration.builder()
                .student(Student.builder().firstname("John").lastname("Doe").build())
                .courses(List.of(course))
                .build();

        when(courseMapper.toCourseDTO(anyList())).thenReturn(List.of(course));
        assertThrows(RegistrationStudentAlreadyInCourseException.class, () -> registrationService.registerStudentToCourse(registration));
    }
}