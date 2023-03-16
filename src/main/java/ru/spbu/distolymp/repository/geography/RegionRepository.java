package ru.spbu.distolymp.repository.geography;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeDto;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.geography.Region;

import java.util.List;
import java.util.Optional;

/**
 * @author Daria Usova
 */
public interface RegionRepository extends PagingAndSortingRepository<Region, Long> {
    List<Region> findByCountryIdAndVisibleOrderByCode(Long id, Visible visible);
    List<RegionNameCodeDto> findRegionsByCountryIdOrderByCode(Long countryId);
    RegionNameCodeDto getRegionNameCodeDtoById(Long id);
    Region findTop1ByOrderByCode();
    List<Region> findRegionsByCountryId(Long countryId);
    Optional<Region> findById(Long id);
}
