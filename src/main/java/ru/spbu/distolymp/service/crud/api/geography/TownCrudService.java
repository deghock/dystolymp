package ru.spbu.distolymp.service.crud.api.geography;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.spbu.distolymp.dto.entity.geography.town.TownDto;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface TownCrudService {

    List<TownDto> getTowns(Sort sort);

    List<TownDto> getTowns(Pageable pageable);

}
