package ru.spbu.distolymp.service.admin.directories.regions.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryNameDto;
import ru.spbu.distolymp.dto.admin.directories.regions.RegionDetailsDto;
import ru.spbu.distolymp.dto.admin.directories.regions.RegionFilter;
import ru.spbu.distolymp.dto.entity.geography.region.RegionDto;
import ru.spbu.distolymp.entity.geography.Region;
import ru.spbu.distolymp.service.admin.directories.regions.api.RegionService;
import ru.spbu.distolymp.service.crud.api.geography.CountryCrudService;
import ru.spbu.distolymp.service.crud.api.geography.RegionCrudService;
import ru.spbu.distolymp.service.crud.api.geography.TownCrudService;
import ru.spbu.distolymp.util.admin.directories.regions.RegionSpecsConverter;

import java.util.ArrayList;
import java.util.List;

@Log4j
@Service
public class RegionServiceImpl implements RegionService {

    private static final Sort SORT_BY_ID_ASC = Sort.by("id").ascending();
    private static final String REGIONS_PARAM = "regions";
    private static final String COUNTRIES_PARAM = "countries";

    private final RegionCrudService regionCrudService;
    private final CountryCrudService countryCrudService;
    private final TownCrudService townCrudService;

    public RegionServiceImpl(RegionCrudService regionCrudService, CountryCrudService countryCrudService, TownCrudService townCrudService) {
        this.regionCrudService = regionCrudService;
        this.countryCrudService = countryCrudService;
        this.townCrudService = townCrudService;
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllRegionsModelMap(ModelMap modelMap, int numberOfRegionsDisplayed) {
        List<RegionDto> regions = getRegions(numberOfRegionsDisplayed);
        List<Long> idList = new ArrayList<>();

        modelMap.put(REGIONS_PARAM, regions);
        modelMap.put("idList", idList);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowRegionDetailsModelMap(ModelMap modelMap, Long regionId) {
        RegionDetailsDto regionDetailsDto = regionCrudService.getRegionDetailsById(regionId);
        modelMap.put("region", regionDetailsDto);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowEditPageModelMap(ModelMap modelMap) {
        List<CountryNameDto> countryDtoList = countryCrudService.getAllCountries();
        RegionDetailsDto regionDetailsDto = getNewRegionDetailsDto();
        modelMap.put(COUNTRIES_PARAM, countryDtoList);
        modelMap.put("region", regionDetailsDto);
    }

    @Override
    public void fillShowEditPageModelMap(ModelMap modelMap, Long regionId) {
        List<CountryNameDto> countryDtoList = countryCrudService.getAllCountries();
        RegionDetailsDto regionDetailsDto = regionCrudService.getRegionDetailsById(regionId);
        modelMap.put(COUNTRIES_PARAM, countryDtoList);
        modelMap.put("region", regionDetailsDto);
    }

    @Override
    @Transactional
    public void saveOrUpdate(RegionDetailsDto regionDetailsDto) {
        regionCrudService.saveOrUpdate(regionDetailsDto);
    }

    @Override
    public void deleteRegionsByIds(Long[] ids) {
        regionCrudService.deleteRegionsByIds(ids);
    }

    private RegionDetailsDto getNewRegionDetailsDto() {
        RegionDetailsDto regionDetailsDto = new RegionDetailsDto();
        regionDetailsDto.setVisible(true);
        regionDetailsDto.setEditing(false);
        regionDetailsDto.setTowns(new ArrayList<>());
        return regionDetailsDto;
    }

    @Override
    public void fillShowRegionTableByFilterModelMap(RegionFilter regionFilter, ModelMap modelMap) {
        int resultSize = regionFilter.getResultSize();
        Specification<Region> specs = RegionSpecsConverter.toSpecs(regionFilter);
        List<RegionDto> regionDtoList;
        if (specs == null) {
            regionDtoList = getRegions(resultSize);
            modelMap.put(REGIONS_PARAM, regionDtoList);
            return;
        }
        if (resultSize == 0) {
            regionDtoList = regionCrudService.getRegionsBySpec(specs, SORT_BY_ID_ASC);
            modelMap.put(REGIONS_PARAM, regionDtoList);
            return;
        }
        Pageable sortedByNameAsc = getPageableSortedByName(resultSize);
        regionDtoList = regionCrudService.getRegionsBySpec(specs, sortedByNameAsc);
        modelMap.put(REGIONS_PARAM, regionDtoList);
    }

    private List<RegionDto> getRegions(int numberOfRegionsDisplayed) {
        if (numberOfRegionsDisplayed <= 0) {
            return regionCrudService.getRegions(SORT_BY_ID_ASC);
        }
        Pageable pageable = getPageableSortedByName(numberOfRegionsDisplayed);
        return regionCrudService.getRegions(pageable);
    }

    private Pageable getPageableSortedByName(int numberOfRegionsDisplayed) {
        return PageRequest.of(0, numberOfRegionsDisplayed, SORT_BY_ID_ASC);
    }
}
