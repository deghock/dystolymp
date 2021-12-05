package ru.spbu.distolymp.service.crud.api.geography;

import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeDto;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface RegionCrudService {
    List<RegionNameCodeDto> getAllRussianRegions();
}
