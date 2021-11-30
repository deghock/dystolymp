package ru.spbu.distolymp.service.admin.directories.schools.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.schools.CreateSchoolDto;
import ru.spbu.distolymp.dto.admin.directories.schools.SchoolDetailsDto;
import ru.spbu.distolymp.dto.admin.directories.schools.SchoolFilter;
import ru.spbu.distolymp.dto.entity.education.SchoolDto;
import ru.spbu.distolymp.dto.entity.education.SchoolTypeDto;
import ru.spbu.distolymp.dto.entity.geography.country.CountryDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeDto;
import ru.spbu.distolymp.dto.entity.geography.town.TownNameDto;
import ru.spbu.distolymp.entity.education.School;
import ru.spbu.distolymp.entity.geography.Country;
import ru.spbu.distolymp.entity.geography.Region;
import ru.spbu.distolymp.entity.geography.Town;
import ru.spbu.distolymp.exception.admin.directories.schools.SchoolServiceException;
import ru.spbu.distolymp.mapper.admin.directories.schools.SchoolDetailsMapper;
import ru.spbu.distolymp.mapper.entity.education.SchoolMapper;
import ru.spbu.distolymp.mapper.entity.education.SchoolTypeMapper;
import ru.spbu.distolymp.mapper.entity.geography.CountryMapper;
import ru.spbu.distolymp.repository.education.SchoolRepository;
import ru.spbu.distolymp.repository.education.SchoolTypeRepository;
import ru.spbu.distolymp.repository.geography.CountryRepository;
import ru.spbu.distolymp.repository.geography.RegionRepository;
import ru.spbu.distolymp.repository.geography.TownRepository;
import ru.spbu.distolymp.service.admin.directories.schools.api.SchoolService;
import ru.spbu.distolymp.service.crud.api.division.DivisionCrudService;
import ru.spbu.distolymp.service.crud.impl.education.SchoolCrudServiceImpl;
import ru.spbu.distolymp.util.admin.directories.schools.SchoolSpecsConverter;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Maxim Andreev
 */
@Log4j
@Service
public class SchoolServiceImpl extends SchoolCrudServiceImpl implements SchoolService {

    public SchoolServiceImpl(SchoolMapper schoolMapper, SchoolRepository schoolRepository,
                             CountryRepository countryRepository, RegionRepository regionRepository,
                             CountryMapper countryMapper, TownRepository townRepository, SchoolTypeRepository schoolTypeRepository,
                             SchoolTypeMapper schoolTypeMapper, SchoolDetailsMapper schoolDetailsMapper,
                             DivisionCrudService divisionCrudService) {
        super(schoolMapper, schoolRepository, divisionCrudService);
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
        this.regionRepository = regionRepository;
        this.townRepository = townRepository;
        this.schoolTypeRepository = schoolTypeRepository;
        this.schoolTypeMapper = schoolTypeMapper;
        this.schoolDetailsMapper = schoolDetailsMapper;

    }

    private static final Sort SORT_BY_NAME_ASC = Sort.by("title").ascending();
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final RegionRepository regionRepository;
    private final TownRepository townRepository;
    private final SchoolTypeRepository schoolTypeRepository;
    private final SchoolTypeMapper schoolTypeMapper;
    private final SchoolDetailsMapper schoolDetailsMapper;

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllSchoolsModelMap(ModelMap modelMap, int numberOfSchoolsDisplayed) {
        List<SchoolDto> schools = getSchools(numberOfSchoolsDisplayed);
        List<CountryDto> countries = countryMapper.toDtoList(countryRepository.findAll());
        modelMap.put("schools", schools);
        modelMap.put("countries", countries);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillAddNewSchoolModelMap(ModelMap modelMap) {
        List<CountryDto> countries = countryMapper.toDtoList(countryRepository.findAll());
        List<RegionNameCodeDto> regions = regionRepository.findRegionsByCountryIdOrderByCode((long)1);
        List<TownNameDto> towns = townRepository.getTownsByRegionIdOrderByName((long)1);
        List<SchoolTypeDto> schoolTypes = schoolTypeMapper.toDtoList(schoolTypeRepository.findAll());

        modelMap.put("school", getNewSchoolDto());
        modelMap.put("countries", countries);
        modelMap.put("regions", regions);
        modelMap.put("towns", towns);
        modelMap.put("schoolTypes", schoolTypes);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillEditPage(ModelMap modelMap, Long schoolId) {
        SchoolDetailsDto details = getSchoolDetailsById(schoolId);
        List<CountryDto> countries = countryMapper.toDtoList(countryRepository.findAll());
        List<RegionNameCodeDto> regions = regionRepository.findRegionsByCountryIdOrderByCode((long)1);
        List<TownNameDto> towns = townRepository.getTownsByRegionIdOrderByName(townRepository.getTownById(details.getTownId()).getRegion().getId());
        List<SchoolTypeDto> schoolTypes = schoolTypeMapper.toDtoList(schoolTypeRepository.findAll());

        modelMap.put("countries", countries);
        modelMap.put("regions", regions);
        modelMap.put("towns", towns);
        modelMap.put("schoolTypes", schoolTypes);


        modelMap.put("details", details);
    }

    private Pageable getPageableSortedByName(int resultSize) {
        return PageRequest.of(0, resultSize, SORT_BY_NAME_ASC);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchoolDto> getSchoolsBy(SchoolFilter schoolFilter) {
        int resultSize = schoolFilter.getResultSize();
        Specification<School> specs = SchoolSpecsConverter.toSpecs(schoolFilter);

        if (specs == null) {
            return getSchools(resultSize);
        }

        if (resultSize <= 0){
            return getSchools(specs, SORT_BY_NAME_ASC);
        }

        Pageable sortedByNameAsc = getPageableSortedByName(resultSize);
        return getSchools(specs, sortedByNameAsc);
    }

    private List<SchoolDto> getSchools(int resultSize) {
        if (resultSize <= 0) {
            return getSchools(SORT_BY_NAME_ASC);
        }

        Pageable pageable = getPageableSortedByName(resultSize);
        return getSchools(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public SchoolDto getNewSchoolDto() {
        SchoolDto schoolDto = new SchoolDto();
        return schoolDto;
    }

    @Override
    @Transactional(readOnly = true)
    public CreateSchoolDto getNewCreateSchoolDto() {
        CreateSchoolDto createSchoolDto = new CreateSchoolDto();
        return createSchoolDto;
    }

    @Override
    @Transactional(readOnly = true)
    public SchoolDetailsDto getSchoolDetailsById(Long id) {
        try {
            School school = schoolRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);

            SchoolDetailsDto schoolDetails = schoolDetailsMapper.toDto(school);
            initAdditionalFields(schoolDetails);

            return schoolDetails;
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while getting school by id=" + id, e);
            throw new SchoolServiceException();
        }
    }

    private void initAdditionalFields(SchoolDetailsDto schoolDetails) {
        Long townId = schoolDetails.getTownId();
        Town town = townRepository.getTownById(townId);
        if(town != null){
            schoolDetails.setTownName(town.getName());
            Region region = town.getRegion();
            if(region != null){
                schoolDetails.setRegionName(region.getName());
                Country country = region.getCountry();
                if(country != null){
                    schoolDetails.setCountryName(country.getName());
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegionNameCodeDto> fillShowRegionsByCountryId(Long countryId){
        List<RegionNameCodeDto> regions = regionRepository.findRegionsByCountryIdOrderByCode(countryId);
        return regions;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TownNameDto> fillShowTownsByRegionId(Long regionId){
        List<TownNameDto> towns = townRepository.getTownsByRegionIdOrderByName(regionId);
        return towns;
    }


    @Override
    @Transactional(readOnly = true)
    public SchoolDto getSchoolDtoById(Long id) {
        return getSchoolById(id);
    }

    @Override
    @Transactional
    public void deleteSchoolsById(Long[] ids) {
        if (ids.length > 0) {
            deleteSchoolsById(Arrays.asList(ids));
        }
    }

}
