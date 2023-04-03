package ru.spbu.distolymp.service.admin.directories.regions.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.regions.RegionDetailsDto;
import ru.spbu.distolymp.dto.admin.directories.regions.RegionFilter;

public interface RegionService {
    void fillShowAllRegionsModelMap(ModelMap modelMap, int numberOfRegionsDisplayed);
    void fillShowRegionDetailsModelMap(ModelMap modelMap, Long regionId);
    void fillShowRegionTableByFilterModelMap(RegionFilter regionFilter, ModelMap modelMap);
    void fillShowEditPageModelMap(ModelMap modelMap);
    void fillShowEditPageModelMap(ModelMap modelMap, Long townId);
    void saveOrUpdate(RegionDetailsDto regionDetailsDto);
    void deleteRegionsByIds(Long[] ids);
}
