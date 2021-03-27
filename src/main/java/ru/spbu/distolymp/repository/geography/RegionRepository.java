package ru.spbu.distolymp.repository.geography;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeDto;
import ru.spbu.distolymp.entity.geography.Region;

import java.util.List;

/**
 * @author Daria Usova
 */
public interface RegionRepository extends PagingAndSortingRepository<Region, Long> {

    List<RegionNameCodeDto> findRegionsByCountryIdOrderByCode(Long countryId);

}
