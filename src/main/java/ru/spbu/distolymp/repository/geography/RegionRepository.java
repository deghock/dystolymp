package ru.spbu.distolymp.repository.geography;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeDto;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.geography.Region;

import java.util.List;
import java.util.Optional;

/**
 * @author Daria Usova
 */
public interface RegionRepository extends PagingAndSortingRepository<Region, Long>, JpaSpecificationExecutor<Region> {
    List<Region> findByCountryIdAndVisibleOrderByCode(Long id, Visible visible);
    List<RegionNameCodeDto> findRegionsByCountryIdOrderByCode(Long countryId);
    RegionNameCodeDto getRegionNameCodeDtoById(Long id);
    Region findTop1ByOrderByCode();
    List<Region> findRegionsByCountryId(Long countryId);
    Optional<Region> findById(Long id);
    List<Region> findAllBy(Sort sort);
    List<Region> findAllBy(Pageable pageable);
    void deleteAllByIdIn(List<Long> idList);
}
