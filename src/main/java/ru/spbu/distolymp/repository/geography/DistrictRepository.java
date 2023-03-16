package ru.spbu.distolymp.repository.geography;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.spbu.distolymp.dto.entity.geography.district.DistrictNameDto;
import ru.spbu.distolymp.entity.geography.District;

import java.util.List;

/**
 * @author Maxim Andreev
 */
public interface DistrictRepository extends PagingAndSortingRepository<District, Long> {
    List<DistrictNameDto> getDistrictsByTownIdOrderByName(Long id);

    District getDistinctById(Long id);
}