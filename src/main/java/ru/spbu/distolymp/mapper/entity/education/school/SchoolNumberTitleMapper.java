package ru.spbu.distolymp.mapper.entity.education.school;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.education.school.SchoolNumberTitleDto;
import ru.spbu.distolymp.entity.education.School;

/**
 * @author Vladislav Konovalov
 */
@Mapper
public interface SchoolNumberTitleMapper {
    SchoolNumberTitleDto toDto(School school);
}
