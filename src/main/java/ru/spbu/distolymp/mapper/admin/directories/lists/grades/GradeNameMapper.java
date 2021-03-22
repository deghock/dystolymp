package ru.spbu.distolymp.mapper.admin.directories.lists.grades;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.spbu.distolymp.dto.admin.directories.lists.grades.GradeNameDto;
import ru.spbu.distolymp.entity.lists.Grade;
import ru.spbu.distolymp.service.crud.api.lists.DivisionCrudService;

/**
 * @author Vladislav Konovalov
 */
@Mapper(uses = {DivisionCrudService.class})
public interface GradeNameMapper {

    @Mapping(target = "divisionId", source = "division.id")
    GradeNameDto toDto(Grade grade);

    @Mapping(target = "division", source = "divisionId")
    Grade toEntity(GradeNameDto gradeNameDto);

}
