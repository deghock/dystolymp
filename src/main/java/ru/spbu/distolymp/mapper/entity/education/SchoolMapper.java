package ru.spbu.distolymp.mapper.entity.education;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.spbu.distolymp.dto.entity.education.SchoolDto;
import ru.spbu.distolymp.entity.education.School;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.service.crud.api.education.SchoolTypeCrudService;
import ru.spbu.distolymp.service.crud.api.geography.DistrictCrudService;
import ru.spbu.distolymp.service.crud.api.geography.TownCrudService;

import java.util.List;
/**
 * @author Maxim Andreev
 */
@Mapper(uses = {TownCrudService.class, DistrictCrudService.class, SchoolTypeCrudService.class})
public interface SchoolMapper {

    @Mapping(target = "townId", source = "town.id")
    @Mapping(target = "districtId", source = "district.id")
    @Mapping(target = "typeId", source = "schoolType.id")
    SchoolDto toDto(School school);

    List<SchoolDto> toDtoList(List<School> schools);

    @Mapping(target = "town", source = "townId")
    @Mapping(target = "district", source = "districtId")
    @Mapping(target = "schoolType", source = "typeId")
    School toEntity(SchoolDto schoolDto);

    default boolean visibleToBoolean(Visible visible) {
        return visible.toBoolean();
    }

    default Visible booleanToVisible(boolean visibleFlag) {
        return Visible.getVisible(visibleFlag);
    }

}
