package ru.spbu.distolymp.mapper.entity.education;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.education.SchoolTypeDto;
import ru.spbu.distolymp.entity.education.SchoolType;
import ru.spbu.distolymp.entity.enumeration.Visible;

import java.util.List;
/**
 * @author Maxim Andreev
 */
@Mapper
public interface SchoolTypeMapper {

    SchoolTypeDto toDto(SchoolType schoolType);

    List<SchoolTypeDto> toDtoList(List<SchoolType> schoolTypes);

    SchoolType toEntity(SchoolTypeDto schoolTypeDto);

    default boolean visibleToBoolean(Visible visible) {
        return visible.toBoolean();
    }

    default Visible booleanToVisible(boolean visibleFlag) {
        return Visible.getVisible(visibleFlag);
    }
}
