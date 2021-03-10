package ru.spbu.distolymp.mapper.entity.education;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.spbu.distolymp.dto.entity.education.PlaceDto;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.education.Place;
import ru.spbu.distolymp.service.crud.api.lists.DivisionCrudService;

import java.util.List;

/**
 * @author Daria Usova
 */
@Mapper(uses = {DivisionCrudService.class})
public interface PlaceMapper {

    @Mapping(target = "divisionId", source = "division.id")
    PlaceDto toDto(Place place);

    List<PlaceDto> toDtoList(List<Place> places);

    @Mapping(target = "division", source = "divisionId")
    Place toEntity(PlaceDto placeDto);

    default boolean visibleToBoolean(Visible visible) {
        return visible.toBoolean();
    }

    default Visible booleanToVisible(boolean visibleFlag) {
        return Visible.getVisible(visibleFlag);
    }

}
