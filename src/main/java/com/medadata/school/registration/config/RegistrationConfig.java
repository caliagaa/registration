package com.medadata.school.registration.config;

import com.medadata.school.registration.mapper.CourseMapper;
import com.medadata.school.registration.mapper.StudentMapper;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RegistrationConfig {

    @Value("${registration.max-students-per-course}")
    private int maxStudentsPerCourse;

    @Value("${registration.student-can-register-max}")
    private int studentCanRegisterMax;

    @Bean
    StudentMapper studentMapper() {
        return StudentMapper.INSTANCE;
    }

    @Bean
    CourseMapper courseMapper() {
        return CourseMapper.INSTANCE;
    }
}
