package ru.spbu.distolymp.mapper.entity.geography;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeCountryDto;
import ru.spbu.distolymp.entity.geography.Region;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Mapper
public interface RegionNameCodeCountryMapper {
    RegionNameCodeCountryDto toDto(Region region);
    List<RegionNameCodeCountryDto> toDtoList(List<Region> regionList);
    Region toEntity(RegionNameCodeCountryDto regionDto);
}
