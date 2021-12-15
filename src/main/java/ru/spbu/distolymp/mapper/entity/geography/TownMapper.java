package ru.spbu.distolymp.mapper.entity.geography;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.geography.town.TownDto;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.geography.Town;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Mapper
public interface TownMapper {

    List<TownDto> toDtoList(List<Town> townList);

    TownDto toDto(Town town);

    Town toEntity(TownDto townDto);

    default boolean visibleToBoolean(Visible visible) {
        return visible.toBoolean();
    }

    default Visible booleanToVisible(boolean visibleFlag) {
        return Visible.getVisible(visibleFlag);
    }

}
