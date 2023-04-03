package ru.spbu.distolymp.mapper.entity.geography;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeDto;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.geography.Region;

import java.util.List;

/**
 * @author Maxim Andreev
 */
@Mapper
public interface RegionMapper {

    RegionNameCodeDto toDto(Region region);

    List<RegionNameCodeDto> toDtoList(List<Region> countries);

    Region toEntity(RegionNameCodeDto regionNameCodeDto);

    default boolean visibleToBoolean(Visible visible) {
        return visible.toBoolean();
    }

    default Visible booleanToVisible(boolean visibleFlag) {
        return Visible.getVisible(visibleFlag);
    }
}
