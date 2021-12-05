package ru.spbu.distolymp.mapper.admin.directories.towns;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.admin.directories.towns.TownDetailsDto;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.geography.Town;

/**
 * @author Vladislav Konovalov
 */
@Mapper
public interface TownDetailsMapper {
    TownDetailsDto toDto(Town town);
    Town toEntity(TownDetailsDto townDto);

    default boolean visibleToBoolean(Visible visible) {
        return visible.toBoolean();
    }
    default Visible booleanToVisible(boolean b) {
        return b ? Visible.yes : Visible.no;
    }
}
