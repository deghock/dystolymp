package ru.spbu.distolymp.mapper.admin.directories.regions;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.admin.directories.regions.RegionDetailsDto;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.geography.Region;

@Mapper
public interface RegionDetailsMapper {
    Region toEntity(RegionDetailsDto regionDetailsDto);
    RegionDetailsDto toDto(Region region);

    default boolean visibleToBoolean(Visible visible) {
        return visible.toBoolean();
    }
    default Visible booleanToVisible(boolean b) {
        return b ? Visible.yes : Visible.no;
    }
}
