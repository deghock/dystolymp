package ru.spbu.distolymp.service.crud.api.geography;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.directories.towns.TownDetailsDto;
import ru.spbu.distolymp.dto.entity.geography.town.TownDto;
import ru.spbu.distolymp.entity.geography.Town;

import java.util.List;
import java.util.Optional;

/**
 * @author Vladislav Konovalov, Maxim Andreev
 */
public interface TownCrudService {
    Town getTownById(Long id);
    List<TownDto> getTowns(Sort sort);
    List<TownDto> getTowns(Pageable pageable);
    TownDetailsDto getTownDetailsById(Long id);
    Town saveOrUpdate(TownDetailsDto townDto);
    Town saveOrUpdate(Town town);
    void deleteTownsByIds(Long[] ids);
    List<TownDto> getTownsBySpec(Specification<Town> spec, Sort sort);
    List<TownDto> getTownsBySpec(Specification<Town> spec, Pageable pageable);
    List<Town> getVisibleTownsInRegion(Long regionId);
    Optional<Town> getTownByRegionIdAndName(Long regionId, String name);
    List<Town> getVisibleTownsInCountryWithNoRegions(Long countryId);
}
