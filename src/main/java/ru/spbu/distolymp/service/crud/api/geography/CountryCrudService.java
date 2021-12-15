package ru.spbu.distolymp.service.crud.api.geography;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryNameDto;
import ru.spbu.distolymp.dto.entity.geography.country.CountryDto;
import ru.spbu.distolymp.entity.geography.Country;

import java.util.List;

/**
 * @author Daria Usova
 */
public interface CountryCrudService {

    void save(CountryDto countryDto);

    CountryDto getCountryByIdOrNull(Long id);

    List<CountryDto> getCountries(Sort sort);

    List<CountryDto> getCountries(Pageable pageable);

    List<CountryDto> getCountries(Specification<Country> specs, Sort sort);

    List<CountryDto> getCountries(Specification<Country> specs, Pageable pageable);

    void update(CountryDto countryDto);

    void saveOrUpdate(CountryDto countryDto);

    void deleteCountriesByIdIn(List<Long> ids);

    List<CountryNameDto> getAllCountries();

    CountryNameDto getCountryByNameOrNull(String name);

}
