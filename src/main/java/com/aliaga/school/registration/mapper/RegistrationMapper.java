package com.aliaga.school.registration.mapper;

import com.aliaga.school.registration.dto.Course;
import com.aliaga.school.registration.dto.Registration;
import com.aliaga.school.registration.repository.entity.CourseEntity;
import com.aliaga.school.registration.repository.entity.RegistrationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface RegistrationMapper {

    RegistrationMapper INSTANCE = Mappers.getMapper( RegistrationMapper.class );

    RegistrationEntity toRegistrationEntity(Registration s );

    @Mapping(source = "student", target = "student")
    @Mapping(source = "student.registrations", target = "courses", qualifiedByName = "toCourses")
    Registration toRegistrationDTO(RegistrationEntity s);

    List<Registration> toRegistrationDTO(List<RegistrationEntity> s);

    @Named("toCourses")
    public default List<Course> toCourses(Set<RegistrationEntity> registrations) {
        return registrations
                .stream()
                .map(RegistrationEntity::getCourse)
                .map(this::toCourse)
                .collect(Collectors.toList());
    }

    public default Course toCourse(CourseEntity ce) {
        return Course.builder()
                .id(ce.getId())
                .name(ce.getName())
                .build();
    }
}
