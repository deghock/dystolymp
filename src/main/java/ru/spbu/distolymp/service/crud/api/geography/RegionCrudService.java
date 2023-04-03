package ru.spbu.distolymp.service.crud.api.geography;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.directories.regions.RegionDetailsDto;
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
    List<RegionDto> getRegions(Sort sort);
    List<RegionDto> getRegions(Pageable pageable);
    RegionDetailsDto getRegionDetailsById(Long id);
    Region saveOrUpdate(RegionDetailsDto regionDetailsDto);
    Optional<Region> getRegionById(Long id);
    List<RegionDto> getVisibleRegionsInRussia();
    List<RegionDto> getRegionsBySpec(Specification<Region> spec, Sort sort);
    List<RegionDto> getRegionsBySpec(Specification<Region> spec, Pageable pageable);
    List<RegionNameCodeDto> getAllRussianRegions();
    RegionNameCodeCountryDto getFirstRegionByCode();
    List<RegionNameCodeCountryDto> getRegionsByCountryId(Long countryId);
    void deleteRegionsByIds(Long[] ids);
}
