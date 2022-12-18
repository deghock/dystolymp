package ru.spbu.distolymp.service.admin.directories.towns.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.towns.TownDetailsDto;
import ru.spbu.distolymp.dto.admin.directories.towns.TownFilter;

/**
 * @author Vladislav Konovalov
 */
public interface TownService {
    void fillShowAllTownsModelMap(ModelMap modelMap, int numbersOfTownsDisplayed);
    void fillShowTownDetailsModelMap(ModelMap modelMap, Long townId);
    void fillShowEditPageModelMap(ModelMap modelMap, Long townId);
    void fillShowEditPageModelMap(ModelMap modelMap);
    void fillFailedEditPageModelMap(ModelMap modelMap);
    void saveOrUpdate(TownDetailsDto townDto);
    void deleteTownsByIds(Long[] ids);
    void fillShowTownTableByFilterModelMap(TownFilter townFilter, ModelMap modelMap);
}
