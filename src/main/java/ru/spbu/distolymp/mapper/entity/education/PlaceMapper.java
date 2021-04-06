package ru.spbu.distolymp.mapper.entity.education;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.education.PlaceDto;
import ru.spbu.distolymp.entity.education.Place;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.service.crud.api.division.DivisionCrudService;

import java.util.List;

/**
 * @author Daria Usova
 */
@Mapper(uses = {DivisionCrudService.class})
public interface PlaceMapper {

    PlaceDto toDto(Place place);

    Place toEntity(PlaceDto placeDto);

    List<PlaceDto> toDtoList(List<Place> places);

    default boolean visibleToBoolean(Visible visible) {
        return visible.toBoolean();
    }

    default Visible booleanToVisible(boolean visibleFlag) {
        return Visible.getVisible(visibleFlag);
    }

}
