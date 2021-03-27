package ru.spbu.distolymp.service.admin.directories.countries.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryDetailsDto;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryFilter;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeDto;
import ru.spbu.distolymp.dto.entity.geography.country.CountryDto;
import ru.spbu.distolymp.dto.entity.geography.town.TownNameDto;
import ru.spbu.distolymp.entity.geography.Country;
import ru.spbu.distolymp.exception.admin.directories.countries.CountryServiceException;
import ru.spbu.distolymp.mapper.admin.directories.countries.CountryDetailsMapper;
import ru.spbu.distolymp.mapper.entity.geography.CountryMapper;
import ru.spbu.distolymp.repository.geography.CountryRepository;
import ru.spbu.distolymp.repository.geography.RegionRepository;
import ru.spbu.distolymp.repository.geography.TownRepository;
import ru.spbu.distolymp.service.admin.directories.countries.api.CountryService;
import ru.spbu.distolymp.service.crud.impl.geography.CountryCrudServiceImpl;
import ru.spbu.distolymp.util.admin.directories.countries.CountrySpecsConverter;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Daria Usova
 */
@Log4j
@Service
public class CountryServiceImpl extends CountryCrudServiceImpl implements CountryService {

    private static final Sort SORT_BY_NAME_ASC = Sort.by("name").ascending();
    private final CountryDetailsMapper countryDetailsMapper;
    private final RegionRepository regionRepository;
    private final TownRepository townRepository;

    public CountryServiceImpl(CountryRepository countryRepository, CountryMapper countryMapper,
                              CountryDetailsMapper countryDetailsMapper, RegionRepository regionRepository,
                              TownRepository townRepository) {
        super(countryRepository, countryMapper);
        this.countryDetailsMapper = countryDetailsMapper;
        this.regionRepository = regionRepository;
        this.townRepository = townRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllCountriesModelMap(ModelMap modelMap, int numberOfCountriesDisplayed) {
        List<CountryDto> countries = getCountries(numberOfCountriesDisplayed);
        List<Long> idList = new ArrayList<>();

        modelMap.put("countries", countries);
        modelMap.put("idList", idList);
    }

    private Pageable getPageableSortedByName(int resultSize) {
        return PageRequest.of(0, resultSize, SORT_BY_NAME_ASC);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryDto> getCountriesBy(CountryFilter countryFilter) {
        int resultSize = countryFilter.getResultSize();
        Specification<Country> specs = CountrySpecsConverter.toSpecs(countryFilter);

        if (specs == null) {
            return getCountries(resultSize);
        }

        if (resultSize <= 0) {
            return getCountries(specs, SORT_BY_NAME_ASC);
        }

        Pageable sortedByNameAsc = getPageableSortedByName(resultSize);
        return getCountries(specs, sortedByNameAsc);
    }

    private List<CountryDto> getCountries(int resultSize) {
        if (resultSize <= 0) {
            return getCountries(SORT_BY_NAME_ASC);
        }

        Pageable pageable = getPageableSortedByName(resultSize);
        return getCountries(pageable);
    }

    @Override
    public CountryDto getNewCountryDto() {
        CountryDto countryDto = new CountryDto();
        countryDto.setVisible(true);

        return countryDto;
    }

    @Override
    @Transactional(readOnly = true)
    public CountryDetailsDto getCountryDetailsById(Long id) {
        try {
            Country country = countryRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);

            CountryDetailsDto countryDetails = countryDetailsMapper.toDto(country);
            initRegionsOrTowns(countryDetails);

            return countryDetails;
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while getting country by id=" + id, e);
            throw new CountryServiceException();
        }
    }

    private void initRegionsOrTowns(CountryDetailsDto countryDetails) {
        Long countryId = countryDetails.getCountryId();
        List<RegionNameCodeDto> regions = regionRepository.findRegionsByCountryIdOrderByCode(countryId);

        if (regions.size() > 1) {
            countryDetails.setRegions(regions);
        } else {
            Long regionId = regions.get(0).getId();
            List<TownNameDto> towns = townRepository.getTownsByRegionIdOrderByName(regionId);
            countryDetails.setTowns(towns);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public CountryDto getCountryById(Long id) {
        if (id == null) {
            throw new CountryServiceException();
        }

        return getCountryByIdOrNull(id);
    }

    @Override
    @Transactional
    public void deleteCountriesWithIdIn(Long[] idList) {
        if (idList.length > 0) {
            deleteCountriesByIdIn(Arrays.asList(idList));
        }
    }

}