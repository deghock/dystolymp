package ru.spbu.distolymp.service.admin.directories.towns.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryNameDto;
import ru.spbu.distolymp.dto.admin.directories.towns.TownDetailsDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeDto;
import ru.spbu.distolymp.dto.entity.geography.town.TownDto;
import ru.spbu.distolymp.mapper.admin.directories.towns.TownDetailsMapper;
import ru.spbu.distolymp.mapper.entity.geography.TownMapper;
import ru.spbu.distolymp.repository.geography.TownRepository;
import ru.spbu.distolymp.service.admin.directories.towns.api.TownService;
import ru.spbu.distolymp.service.crud.api.geography.CountryCrudService;
import ru.spbu.distolymp.service.crud.api.geography.RegionCrudService;
import ru.spbu.distolymp.service.crud.impl.geography.TownCrudServiceImpl;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Service
public class TownServiceImpl extends TownCrudServiceImpl implements TownService {
    private static final Sort SORT_BY_NAME_ASC = Sort.by("name").ascending();
    private final CountryCrudService countryCrudService;
    private final RegionCrudService regionCrudService;

    public TownServiceImpl(TownRepository townRepository,
                           TownMapper townMapper,
                           TownDetailsMapper townDetailsMapper,
                           @Qualifier("countryCrudServiceImpl") CountryCrudService countryCrudService,
                           RegionCrudService regionCrudService) {
        super(townRepository, townMapper, townDetailsMapper);
        this.countryCrudService = countryCrudService;
        this.regionCrudService = regionCrudService;
    }

    @Override
    public void fillShowAllTownsModelMap(ModelMap modelMap, int numbersOfTownsDisplayed) {
        List<TownDto> townList = getTowns(numbersOfTownsDisplayed);
        List<CountryNameDto> countryList = countryCrudService.getAllCountries();
        modelMap.put("towns", townList);
        modelMap.put("countries", countryList);
    }

    private List<TownDto> getTowns(int numbersOfTownsDisplayed) {
        if (numbersOfTownsDisplayed <= 0) {
            return getTowns(SORT_BY_NAME_ASC);
        }
        Pageable pageable = PageRequest.of(0, numbersOfTownsDisplayed, SORT_BY_NAME_ASC);
        return getTowns(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowTownDetailsModelMap(ModelMap modelMap, Long townId) {
        TownDetailsDto town = getTownDetailsById(townId);
        modelMap.put("town", town);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowEditPageModelMap(ModelMap modelMap, Long townId) {
        List<RegionNameCodeDto> regionDtoList = regionCrudService.getAllRussianRegions();
        List<CountryNameDto> countryDtoList = countryCrudService.getAllCountries();
        TownDetailsDto townDto = getTownDetailsById(townId);
        modelMap.put("countries", countryDtoList);
        modelMap.put("regions", regionDtoList);
        modelMap.put("town", townDto);
    }
}
