package ru.spbu.distolymp.repository.geography;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.spbu.distolymp.dto.entity.geography.town.TownNameDto;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.geography.Town;

import java.util.List;
import java.util.Optional;

/**
 * @author Daria Usova, Vladislav Konovalov
 */
public interface TownRepository extends PagingAndSortingRepository<Town, Long>, JpaSpecificationExecutor<Town> {
    Optional<Town> findFirstByRegionIdAndNameIgnoreCase(Long id, String name);

    List<Town> findByRegionIdAndVisible(Long regionId, Visible visible);
    List<TownNameDto> getTownsByRegionIdOrderByName(Long id);
    List<Town> getTownsByRegionIdAndVisibleOrderByName(Long id, Visible visible);
    List<Town> findAllBy(Pageable pageable);
    List<Town> findAllBy(Sort sort);
    void deleteAllByIdIn(List<Long> idList);
}
