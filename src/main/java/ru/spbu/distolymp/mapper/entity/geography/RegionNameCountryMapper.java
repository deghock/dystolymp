package ru.spbu.distolymp.mapper.entity.geography;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCountryDto;
import ru.spbu.distolymp.entity.geography.Region;

/**
 * @author Vladislav Konovalov
 */
@Mapper
public interface RegionNameCountryMapper {
    RegionNameCountryDto toDto(Region region);
}
