package ru.spbu.distolymp.service.crud.api.geography;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.directories.towns.TownDetailsDto;
import ru.spbu.distolymp.dto.entity.geography.town.TownDto;
import ru.spbu.distolymp.entity.geography.Town;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface TownCrudService {
    List<TownDto> getTowns(Sort sort);
    List<TownDto> getTowns(Pageable pageable);
    TownDetailsDto getTownDetailsById(Long id);
    void saveOrUpdate(TownDetailsDto townDto);
    void deleteTownsByIds(Long[] ids);
    List<TownDto> getTownsBySpec(Specification<Town> spec, Sort sort);
    List<TownDto> getTownsBySpec(Specification<Town> spec, Pageable pageable);
}
