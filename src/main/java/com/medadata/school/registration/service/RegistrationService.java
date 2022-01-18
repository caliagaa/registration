package com.medadata.school.registration.service;

import com.medadata.school.registration.config.RegistrationConfig;
import com.medadata.school.registration.dto.Registration;
import com.medadata.school.registration.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final RegistrationConfig registrationConfig;

    private final RegistrationRepository registrationRepository;

    public RegistrationService(RegistrationConfig registrationConfig, RegistrationRepository registrationRepository) {
        this.registrationConfig = registrationConfig;
        this.registrationRepository = registrationRepository;
    }

    public void registerStudentToCourse(Registration registration) {

    }

    public void getAllRegistrations() {

    }

    public void getStudentsByCourse() {

    }

    public void getCoursesByStudent() {

    }
}
