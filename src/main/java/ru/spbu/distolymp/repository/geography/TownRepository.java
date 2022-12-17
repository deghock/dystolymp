package ru.spbu.distolymp.repository.geography;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.spbu.distolymp.dto.entity.geography.town.TownNameDto;
import ru.spbu.distolymp.entity.geography.Town;

import java.util.List;

/**
 * @author Daria Usova
 */
public interface TownRepository extends PagingAndSortingRepository<Town, Long> {

    List<TownNameDto> getTownsByRegionIdOrderByName(Long id);

}
