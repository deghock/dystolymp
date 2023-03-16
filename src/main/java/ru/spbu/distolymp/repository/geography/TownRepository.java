package ru.spbu.distolymp.repository.geography;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.spbu.distolymp.dto.entity.geography.town.TownNameDto;
import ru.spbu.distolymp.entity.geography.Town;

import java.util.List;

/**
 * @author Daria Usova
 */
public interface TownRepository extends CrudRepository<Town, Long> {

    List<TownNameDto> getTownsByRegionIdOrderByName(Long id);

    TownNameDto getTownNameDtoById(Long id);

    Town getTownById(Long id);
}
