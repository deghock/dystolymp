package ru.spbu.distolymp.service.crud.impl.geography;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryDetailsDto;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryNameDto;
import ru.spbu.distolymp.dto.admin.directories.regions.RegionDetailsDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeCountryDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeDto;
import ru.spbu.distolymp.dto.entity.geography.town.TownNameDto;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.geography.Country;
import ru.spbu.distolymp.entity.geography.Region;
import ru.spbu.distolymp.entity.geography.Town;
import ru.spbu.distolymp.exception.common.ResourceNotFoundException;
import ru.spbu.distolymp.exception.common.TechnicalException;
import ru.spbu.distolymp.exception.crud.geography.RegionCrudServiceException;
import ru.spbu.distolymp.mapper.admin.directories.regions.RegionDetailsMapper;
import ru.spbu.distolymp.mapper.entity.geography.RegionNameCodeCountryMapper;
import ru.spbu.distolymp.repository.geography.RegionRepository;
import ru.spbu.distolymp.repository.geography.TownRepository;
import ru.spbu.distolymp.service.crud.api.geography.CountryCrudService;
import ru.spbu.distolymp.service.crud.api.geography.RegionCrudService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final TownRepository townRepository;
    private final CountryCrudService countryCrudServiceImpl;
    private final RegionNameCodeCountryMapper regionMapper;

    @Override
    public List<RegionDto> getRegions(Sort sort) {
        try {
            return regionRepository.findAllBy(sort)
                    .stream()
                    .map(Region::toDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            log.error("An error occurred while getting towns", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<RegionDto> getRegions(Pageable pageable) {
        try {
            return regionRepository.findAllBy(pageable)
                    .stream()
                    .map(Region::toDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            log.error("An error occurred while getting towns", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public RegionDetailsDto getRegionDetailsById(Long id) {
        try {
            Region region = regionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            RegionDetailsDto regionDetailsDto = toDto(region);
            initTowns(regionDetailsDto);
            return regionDetailsDto;
        } catch (DataAccessException e) {
            log.error("An error occurred while getting a region by id=" + id, e);
            throw new TechnicalException();
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    private void initTowns(RegionDetailsDto regionDetailsDto) {
        List<TownNameDto> towns = townRepository.getTownsByRegionIdOrderByName(regionDetailsDto.getId());
        regionDetailsDto.setTowns(towns);
    }

    @Override
    @Transactional
    public Region saveOrUpdate(RegionDetailsDto regionDetailsDto) {
        try {
            Region region = toEntity(regionDetailsDto);
            return regionRepository.save(region);
        } catch (DataAccessException e) {
            log.error("An error occurred while saving or updating a region", e);
            throw new TechnicalException();
        }
    }

    public Region toEntity(RegionDetailsDto regionDetailsDto) {
        if ( regionDetailsDto == null ) {
            return null;
        }

        Region region = new Region();

        region.setId( regionDetailsDto.getId() );
        region.setCountry( countryNameDtoToCountry( regionDetailsDto.getCountry() ) );
        region.setName( regionDetailsDto.getName() );
        region.setCode( regionDetailsDto.getCode() );
        region.setVisible( booleanToVisible( regionDetailsDto.isVisible() ) );
        region.setEditing( regionDetailsDto.isEditing() );

        return region;
    }

    public RegionDetailsDto toDto(Region region) {
        if ( region == null ) {
            return null;
        }

        RegionDetailsDto regionDetailsDto = new RegionDetailsDto();

        regionDetailsDto.setId( region.getId() );
        regionDetailsDto.setCode( region.getCode() );
        regionDetailsDto.setName( region.getName() );
        regionDetailsDto.setCountry( countryToCountryNameDto( region.getCountry() ) );
        regionDetailsDto.setVisible( visibleToBoolean( region.getVisible() ) );
        regionDetailsDto.setEditing( region.isEditing() );

        return regionDetailsDto;
    }

    private Country countryNameDtoToCountry(CountryNameDto countryNameDto) {
        if ( countryNameDto == null ) {
            return null;
        }

        Country country = new Country();

        country.setId( countryNameDto.getId() );
        country.setName( countryNameDto.getName() );

        return country;
    }

    private CountryNameDto countryToCountryNameDto(Country country) {
        if ( country == null ) {
            return null;
        }

        CountryNameDto countryNameDto = new CountryNameDto();

        countryNameDto.setId( country.getId() );
        countryNameDto.setName( country.getName() );

        return countryNameDto;
    }

    private boolean visibleToBoolean(Visible visible) {
        return visible.toBoolean();
    }
    private Visible booleanToVisible(boolean b) {
        return b ? Visible.yes : Visible.no;
    }

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
    public List<RegionDto> getRegionsBySpec(Specification<Region> spec, Sort sort) {
        try {
            return regionRepository.findAll(spec, sort)
                    .stream()
                    .map(Region::toDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            log.error("An error occurred while getting regions by specs", e);
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegionDto> getRegionsBySpec(Specification<Region> spec, Pageable pageable) {
        try {
            return regionRepository.findAll(spec, pageable).getContent()
                    .stream()
                    .map(Region::toDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            log.error("An error occurred while getting towns by specs", e);
            throw new TechnicalException();
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
    @Transactional
    public void deleteRegionsByIds(Long[] ids) {
        try {
            regionRepository.deleteAllByIdIn(Arrays.asList(ids));
        } catch (DataAccessException e) {
            log.error("An error occurred while deleting towns by ids", e);
            throw new TechnicalException();
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
