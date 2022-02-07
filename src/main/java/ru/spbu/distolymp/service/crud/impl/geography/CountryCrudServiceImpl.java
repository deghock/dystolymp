package ru.spbu.distolymp.service.crud.impl.geography;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryNameDto;
import ru.spbu.distolymp.dto.entity.geography.country.CountryDto;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.geography.Country;
import ru.spbu.distolymp.entity.geography.Region;
import ru.spbu.distolymp.exception.crud.geography.CountryCrudServiceException;
import ru.spbu.distolymp.mapper.admin.directories.countries.CountryNameMapper;
import ru.spbu.distolymp.mapper.entity.geography.CountryMapper;
import ru.spbu.distolymp.repository.geography.CountryRepository;
import ru.spbu.distolymp.service.crud.api.geography.CountryCrudService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Daria Usova
 */
@Log4j
@Service
@RequiredArgsConstructor
public class CountryCrudServiceImpl implements CountryCrudService {

    protected final CountryRepository countryRepository;
    protected final CountryMapper countryMapper;
    private final CountryNameMapper countryNameMapper;
    private static final String ERROR_MSG = "An error occurred while getting countries";

    @Override
    @Transactional
    public List<CountryDto> getCountries(Sort sort) {
        try {
            List<Country> countries = countryRepository.findAllBy(sort);
            return countryMapper.toDtoList(countries);
        } catch (DataAccessException e) {
            log.error(ERROR_MSG, e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryDto> getCountries(Pageable pageable) {
        try {
            List<Country> countries = countryRepository.findAllBy(pageable);
            return countryMapper.toDtoList(countries);
        } catch (DataAccessException e) {
            log.error(ERROR_MSG, e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public Country save(CountryDto countryDto) {
        try {
            Country country = countryMapper.toEntity(countryDto);
            Region region = getNewRegion(country);

            country.setRegions(Collections.singletonList(region));

            return countryRepository.save(country);
        } catch (DataAccessException e) {
            log.error("An error occurred while saving country", e);
            throw new CountryCrudServiceException();
        }
    }

    private Region getNewRegion(Country country) {
        Region region = new Region();
        region.setCode("100");
        region.setVisible(Visible.yes);
        region.setEditing(true);
        region.setCountry(country);
        region.setName(" ");

        return region;
    }

    @Override
    @Transactional
    public void update(CountryDto countryDto) {
        try {
            Country country = countryRepository.findById(countryDto.getId())
                    .orElseThrow(EntityNotFoundException::new);

            // explicit mapping because I'm not sure if the field "editing" can be changed
            country.setVisible(Visible.getVisible(countryDto.isVisible()));
            country.setName(countryDto.getName());
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while updating country with id=" + countryDto.getId(), e);
            throw new CountryCrudServiceException();
        }
    }

    @Override
    @Transactional
    public void saveOrUpdate(CountryDto countryDto) {
        if (countryDto.getId() == null || !countryRepository.findById(countryDto.getId()).isPresent()) {
            save(countryDto);
        } else {
            update(countryDto);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CountryDto getCountryByIdOrNull(Long id) {
        try {
            return countryRepository.findById(id)
                    .map(countryMapper::toDto)
                    .orElse(null);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting country by id=" + id, e);
            throw new CountryCrudServiceException();
        }
    }

    @Override
    @Transactional
    public void deleteCountriesByIdIn(List<Long> idList) {
        try {
            countryRepository.deleteAllByIdIn(idList);
        } catch (DataAccessException e) {
            log.error("An error occurred while deleting countries by id in list", e);
            throw new CountryCrudServiceException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryDto> getCountries(Specification<Country> specs, Pageable pageable) {
        try {
            List<Country> countries = countryRepository.findAll(specs, pageable).getContent();
            return countryMapper.toDtoList(countries);
        } catch (DataAccessException e) {
            log.error(ERROR_MSG, e);
            throw new CountryCrudServiceException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryDto> getCountries(Specification<Country> specs, Sort sort) {
        try {
            List<Country> countries = countryRepository.findAll(specs, sort);
            return countryMapper.toDtoList(countries);
        } catch (DataAccessException e) {
            log.error(ERROR_MSG, e);
            throw new CountryCrudServiceException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryNameDto> getAllCountries() {
        try {
            List<Country> countryList = countryRepository.findAll();
            return countryNameMapper.toDtoList(countryList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting all countries", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Country> getCountryByName(String name) {
        try {
            return countryRepository.findCountryByName(name);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting a country with name=" + name, e);
            throw new CountryCrudServiceException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Country> getCountryById(Long id) {
        try {
            return countryRepository.findById(id);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting country by id=" + id, e);
            throw new CountryCrudServiceException();
        }
    }

}
