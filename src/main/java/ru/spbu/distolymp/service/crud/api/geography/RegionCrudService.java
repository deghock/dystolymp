package ru.spbu.distolymp.service.crud.api.geography;

import ru.spbu.distolymp.dto.entity.geography.region.RegionDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeCountryDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeDto;
import ru.spbu.distolymp.entity.geography.Region;

import java.util.List;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
public interface RegionCrudService {
    Optional<Region> getRegionById(Long id);
    List<RegionDto> getVisibleRegionsInRussia();
    List<RegionNameCodeDto> getAllRussianRegions();
    RegionNameCodeCountryDto getFirstRegionByCode();
    List<RegionNameCodeCountryDto> getRegionsByCountryId(Long countryId);
}
