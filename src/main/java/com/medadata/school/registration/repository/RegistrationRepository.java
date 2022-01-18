package com.medadata.school.registration.repository;

import com.medadata.school.registration.repository.entity.RegistrationEntity;
import org.springframework.data.repository.CrudRepository;

public interface RegistrationRepository extends CrudRepository<RegistrationEntity, Long> {

}
