package ru.spbu.distolymp.service.crud.impl.geography;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.geography.region.RegionDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeCountryDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeDto;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.geography.Country;
import ru.spbu.distolymp.entity.geography.Region;
import ru.spbu.distolymp.exception.crud.geography.RegionCrudServiceException;
import ru.spbu.distolymp.mapper.entity.geography.RegionNameCodeCountryMapper;
import ru.spbu.distolymp.repository.geography.RegionRepository;
import ru.spbu.distolymp.service.crud.api.geography.CountryCrudService;
import ru.spbu.distolymp.service.crud.api.geography.RegionCrudService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@RequiredArgsConstructor
public class RegionCrudServiceImpl implements RegionCrudService {

    private static final String RUSSIA = "Россия";
    private final RegionRepository regionRepository;
    private final CountryCrudService countryCrudServiceImpl;
    private final RegionNameCodeCountryMapper regionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RegionDto> getVisibleRegionsInRussia() {
        try {
            Long russiaId = countryCrudServiceImpl.getCountryByName(RUSSIA)
                    .map(Country::getId)
                    .orElseThrow(() -> new RegionCrudServiceException("There is no country with name " + RUSSIA));

            return regionRepository.findByCountryIdAndVisibleOrderByCode(russiaId, Visible.yes)
                    .stream()
                    .map(Region::toDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException | NullPointerException e) {
            log.error("An error occurred while getting visible russian regions", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegionNameCodeDto> getAllRussianRegions() {
        try {
            Long russiaId = countryCrudServiceImpl.getCountryByName(RUSSIA)
                    .map(Country::getId)
                    .orElseThrow(() -> new RegionCrudServiceException("There is no country with name " + RUSSIA));
            return regionRepository.findRegionsByCountryIdOrderByCode(russiaId);
        } catch (DataAccessException | NullPointerException e) {
            log.error("An error occurred while getting russian regions", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public RegionNameCodeCountryDto getFirstRegionByCode() {
        try {
            Region region = regionRepository.findTop1ByOrderByCode();
            return regionMapper.toDto(region);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting first region by code", e);
            throw new RegionCrudServiceException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegionNameCodeCountryDto> getRegionsByCountryId(Long countryId) {
        try {
            List<Region> regionList = regionRepository.findRegionsByCountryId(countryId);
            return regionMapper.toDtoList(regionList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting all regions with countryId=" + countryId, e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Region> getRegionById(Long id) {
        try {
            return regionRepository.findById(id);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting region with id=" + id, e);
            return Optional.empty();
        }
    }

}
