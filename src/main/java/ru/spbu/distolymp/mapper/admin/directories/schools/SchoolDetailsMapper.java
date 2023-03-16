package ru.spbu.distolymp.mapper.admin.directories.schools;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.spbu.distolymp.dto.admin.directories.schools.SchoolDetailsDto;
import ru.spbu.distolymp.entity.education.School;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.service.crud.api.education.SchoolTypeCrudService;
import ru.spbu.distolymp.service.crud.api.geography.DistrictCrudService;
import ru.spbu.distolymp.service.crud.api.geography.TownCrudService;

/**
 * @author Maxim Andreev
 */
@Mapper(uses = {TownCrudService.class, DistrictCrudService.class, SchoolTypeCrudService.class})
public interface SchoolDetailsMapper {

    @Mapping(target = "schoolId", source = "id")
    @Mapping(target = "townId", source = "town.id")
    @Mapping(target = "typeName", source = "schoolType.name")
    SchoolDetailsDto toDto(School school);

    default boolean visibleToBoolean(Visible visible) {
        return visible.toBoolean();
    }

}
