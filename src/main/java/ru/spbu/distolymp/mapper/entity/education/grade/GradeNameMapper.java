package ru.spbu.distolymp.mapper.entity.education.grade;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.education.grade.GradeNameDto;
import ru.spbu.distolymp.entity.education.Grade;

/**
 * @author Vladislav Konovalov
 */
@Mapper
public interface GradeNameMapper {

    GradeNameDto toDto(Grade grade);

    Grade toEntity(GradeNameDto gradeNameDto);

}