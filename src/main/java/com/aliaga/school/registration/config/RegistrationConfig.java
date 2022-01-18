package com.aliaga.school.registration.config;

import com.aliaga.school.registration.mapper.CourseMapper;
import com.aliaga.school.registration.mapper.RegistrationMapper;
import com.aliaga.school.registration.mapper.StudentMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.aliaga.school.registration.repository")
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

    @Bean
    RegistrationMapper registrationMapper() {
        return RegistrationMapper.INSTANCE;
    }
}
