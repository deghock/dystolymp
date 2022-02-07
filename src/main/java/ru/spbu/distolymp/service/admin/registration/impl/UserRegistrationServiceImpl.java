package ru.spbu.distolymp.service.admin.registration.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryNameDto;
import ru.spbu.distolymp.dto.entity.geography.country.CountryDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionDto;
import ru.spbu.distolymp.dto.registration.UserRegistrationDto;
import ru.spbu.distolymp.entity.geography.Country;
import ru.spbu.distolymp.entity.geography.Region;
import ru.spbu.distolymp.entity.geography.Town;
import ru.spbu.distolymp.service.admin.registration.api.UserRegistrationService;
import ru.spbu.distolymp.service.crud.api.geography.CountryCrudService;
import ru.spbu.distolymp.service.crud.api.geography.RegionCrudService;
import ru.spbu.distolymp.service.crud.api.geography.TownCrudService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Daria Usova
 */
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final CountryCrudService countryCrudService;
    private final RegionCrudService regionCrudService;
    private final TownCrudService townCrudService;

    public UserRegistrationServiceImpl(CountryCrudService countryCrudService,
                                       RegionCrudService regionCrudService,
                                       TownCrudService townCrudService) {
        this.countryCrudService = countryCrudService;
        this.regionCrudService = regionCrudService;
        this.townCrudService = townCrudService;
    }

    @Override
    public void fillShowRegistrationPageModelMap(ModelMap modelMap) {
        List<CountryNameDto> countries = countryCrudService.getAllCountries();
        List<RegionDto> regions = regionCrudService.getVisibleRegionsInRussia();

        modelMap.put("countries", countries);
        modelMap.put("regions", regions);
        modelMap.put("dto", new UserRegistrationDto());
    }

    @Override
    public List<String> getTownsByRegionOrCountry(Long regionId, Long countryId) {
        List<Town> towns = new ArrayList<>();
        if (regionId != null) {
            towns = townCrudService.getVisibleTownsInRegion(regionId);
        } else if (countryId != null) {
            towns = townCrudService.getVisibleTownsInCountryWithNoRegions(countryId);
        }

        return towns.stream()
                .map(Town::getName)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void registerUser(UserRegistrationDto dto) {
        processSchoolBlock(dto);
    }

    private void processSchoolBlock(UserRegistrationDto dto) {
        if (dto.getCountryId() != null) {
            CountryDto newCountry = new CountryDto();
            newCountry.setName(dto.getNewCountryName());
            Country country = countryCrudService.getCountryById(dto.getCountryId())
                    .orElseGet(() -> countryCrudService.save(newCountry));

            Region region = country.isRussia() ? regionCrudService.getRegionById(dto.getRegionId())
                    .orElseThrow(() -> new IllegalArgumentException("There is no region with name " + dto.getRegionId()))
                    : country.getRegions().get(0);

            Town newTown = new Town();
            newTown.setName(dto.getTownName());
            newTown.setRegion(region);
            townCrudService.getTownByRegionIdAndName(region.getId(), dto.getTownName())
                    .orElseGet(() -> townCrudService.saveOrUpdate(newTown));
        }
    }

}
