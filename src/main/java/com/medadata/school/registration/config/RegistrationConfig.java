package com.medadata.school.registration.config;

import com.medadata.school.registration.mapper.StudentMapper;
import org.springframework.context.annotation.Bean;

public class RegistrationConfig {

    @Bean
    StudentMapper studentMapper() {
        return StudentMapper.INSTANCE;
    }
}
