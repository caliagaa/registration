package com.medadata.school.registration.mapper;

import com.medadata.school.registration.dto.Course;
import com.medadata.school.registration.repository.entity.CourseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapper {

    CourseMapper INSTANCE = Mappers.getMapper( CourseMapper.class );

    CourseEntity toCourseEntity(Course s );

    Course toCourseDTO(CourseEntity s);
}
