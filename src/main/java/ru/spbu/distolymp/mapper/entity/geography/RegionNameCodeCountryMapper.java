package ru.spbu.distolymp.mapper.entity.geography;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeCountryDto;
import ru.spbu.distolymp.entity.geography.Region;

/**
 * @author Vladislav Konovalov
 */
@Mapper
public interface RegionNameCodeCountryMapper {
    RegionNameCodeCountryDto toDto(Region region);
    Region toEntity(RegionNameCodeCountryDto regionDto);
}
