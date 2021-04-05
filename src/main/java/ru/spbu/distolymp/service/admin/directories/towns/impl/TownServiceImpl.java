package ru.spbu.distolymp.service.admin.directories.towns.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryNameDto;
import ru.spbu.distolymp.dto.entity.geography.town.TownDto;
import ru.spbu.distolymp.mapper.entity.geography.TownMapper;
import ru.spbu.distolymp.repository.geography.TownRepository;
import ru.spbu.distolymp.service.admin.directories.towns.api.TownService;
import ru.spbu.distolymp.service.crud.api.geography.CountryCrudService;
import ru.spbu.distolymp.service.crud.impl.geography.TownCrudServiceImpl;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Service
public class TownServiceImpl extends TownCrudServiceImpl implements TownService {

    private static final Sort SORT_BY_NAME_ASC = Sort.by("name").ascending();

    private final CountryCrudService countryCrudService;

    public TownServiceImpl(TownRepository townRepository, TownMapper townMapper,
                           @Qualifier("countryCrudServiceImpl") CountryCrudService countryCrudService) {
        super(townRepository, townMapper);
        this.countryCrudService = countryCrudService;
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

}
