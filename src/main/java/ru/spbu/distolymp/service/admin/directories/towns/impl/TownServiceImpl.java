package ru.spbu.distolymp.service.admin.directories.towns.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryNameDto;
import ru.spbu.distolymp.dto.admin.directories.towns.TownDetailsDto;
import ru.spbu.distolymp.dto.admin.directories.towns.TownFilter;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeDto;
import ru.spbu.distolymp.dto.entity.geography.town.TownDto;
import ru.spbu.distolymp.entity.geography.Town;
import ru.spbu.distolymp.service.admin.directories.towns.api.TownService;
import ru.spbu.distolymp.service.crud.api.geography.CountryCrudService;
import ru.spbu.distolymp.service.crud.api.geography.RegionCrudService;
import ru.spbu.distolymp.service.crud.api.geography.TownCrudService;
import ru.spbu.distolymp.util.admin.directories.towns.TownSpecsConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Service
public class TownServiceImpl implements TownService {

    private static final Sort SORT_BY_NAME_ASC = Sort.by("name").ascending();
    private static final String COUNTRIES_PARAM = "countries";
    private static final String REGIONS_PARAM = "regions";
    private static final String TOWNS_PARAM = "towns";

    private final TownCrudService townCrudService;
    private final CountryCrudService countryCrudService;
    private final RegionCrudService regionCrudService;

    public TownServiceImpl(TownCrudService townCrudService,
                           CountryCrudService countryCrudService,
                           RegionCrudService regionCrudService) {
        this.townCrudService = townCrudService;
        this.countryCrudService = countryCrudService;
        this.regionCrudService = regionCrudService;
    }

    @Override
    public void fillShowAllTownsModelMap(ModelMap modelMap, int numbersOfTownsDisplayed) {
        List<TownDto> townList = getTowns(numbersOfTownsDisplayed);
        List<CountryNameDto> countryList = countryCrudService.getAllCountries();
        modelMap.put(TOWNS_PARAM, townList);
        modelMap.put(COUNTRIES_PARAM, countryList);
    }

    private List<TownDto> getTowns(int numbersOfTownsDisplayed) {
        if (numbersOfTownsDisplayed <= 0) {
            return townCrudService.getTowns(SORT_BY_NAME_ASC);
        }
        Pageable pageable = getPageableSortedByName(numbersOfTownsDisplayed);
        return townCrudService.getTowns(pageable);
    }

    private Pageable getPageableSortedByName(int numbersOfTownsDisplayed) {
        return PageRequest.of(0, numbersOfTownsDisplayed, SORT_BY_NAME_ASC);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowTownDetailsModelMap(ModelMap modelMap, Long townId) {
        TownDetailsDto town = townCrudService.getTownDetailsById(townId);
        modelMap.put("town", town);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowEditPageModelMap(ModelMap modelMap, Long townId) {
        List<RegionNameCodeDto> regionDtoList = regionCrudService.getAllRussianRegions();
        List<CountryNameDto> countryDtoList = countryCrudService.getAllCountries();
        TownDetailsDto townDto = townCrudService.getTownDetailsById(townId);
        modelMap.put(COUNTRIES_PARAM, countryDtoList);
        modelMap.put(REGIONS_PARAM, regionDtoList);
        modelMap.put("town", townDto);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowEditPageModelMap(ModelMap modelMap) {
        List<RegionNameCodeDto> regionDtoList = regionCrudService.getAllRussianRegions();
        List<CountryNameDto> countryDtoList = countryCrudService.getAllCountries();
        TownDetailsDto townDto = getNewTownDto();
        modelMap.put(COUNTRIES_PARAM, countryDtoList);
        modelMap.put(REGIONS_PARAM, regionDtoList);
        modelMap.put("town", townDto);
    }

    private TownDetailsDto getNewTownDto() {
        TownDetailsDto townDto = new TownDetailsDto();
        townDto.setVisible(true);
        townDto.setEditing(false);
        townDto.setRegion(regionCrudService.getFirstRegionByCode());
        townDto.setSchools(new ArrayList<>());
        return townDto;
    }

    @Override
    public void fillFailedEditPageModelMap(ModelMap modelMap) {
        List<RegionNameCodeDto> regionDtoList = regionCrudService.getAllRussianRegions();
        List<CountryNameDto> countryDtoList = countryCrudService.getAllCountries();
        modelMap.put(COUNTRIES_PARAM, countryDtoList);
        modelMap.put(REGIONS_PARAM, regionDtoList);
    }

    @Override
    public void saveOrUpdate(TownDetailsDto townDto) {
        townCrudService.saveOrUpdate(townDto);
    }

    @Override
    public void deleteTownsByIds(Long[] ids) {
        townCrudService.deleteTownsByIds(ids);
    }

    @Override
    public void fillShowTownTableByFilterModelMap(TownFilter townFilter, ModelMap modelMap) {
        int resultSize = townFilter.getResultSize();
        Specification<Town> specs = TownSpecsConverter.toSpecs(townFilter);
        List<TownDto> townDtoList;
        if (specs == null) {
            townDtoList = getTowns(resultSize);
            modelMap.put(TOWNS_PARAM, townDtoList);
            return;
        }
        if (resultSize == 0) {
            townDtoList = townCrudService.getTownsBySpec(specs, SORT_BY_NAME_ASC);
            modelMap.put(TOWNS_PARAM, townDtoList);
            return;
        }
        Pageable sortedByNameAsc = getPageableSortedByName(resultSize);
        townDtoList = townCrudService.getTownsBySpec(specs, sortedByNameAsc);
        modelMap.put(TOWNS_PARAM, townDtoList);
    }

}
