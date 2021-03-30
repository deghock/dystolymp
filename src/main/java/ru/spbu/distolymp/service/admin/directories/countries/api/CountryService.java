package ru.spbu.distolymp.service.admin.directories.countries.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryDetailsDto;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryFilter;
import ru.spbu.distolymp.dto.entity.geography.country.CountryDto;

import java.util.List;

/**
 * @author Daria Usova
 */
public interface CountryService {

    void fillShowAllCountriesModelMap(ModelMap modelMap, int numberOfCountriesDisplayed);

    List<CountryDto> getCountriesBy(CountryFilter countryFilter);

    CountryDto getNewCountryDto();

    void saveOrUpdate(CountryDto countryDto);

    CountryDetailsDto getCountryDetailsById(Long id);

    CountryDto getCountryById(Long id);

    void deleteCountriesWithIdIn(Long[] idList);

}