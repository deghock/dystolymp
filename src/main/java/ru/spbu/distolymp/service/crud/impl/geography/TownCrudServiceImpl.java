package ru.spbu.distolymp.service.crud.impl.geography;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.entity.geography.Town;
import ru.spbu.distolymp.repository.geography.TownRepository;
import ru.spbu.distolymp.service.crud.api.geography.TownCrudService;
import javax.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.directories.towns.TownDetailsDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeCountryDto;
import ru.spbu.distolymp.dto.entity.geography.town.TownDto;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.geography.Country;
import ru.spbu.distolymp.entity.geography.Region;
import ru.spbu.distolymp.entity.geography.Town;
import ru.spbu.distolymp.exception.common.ResourceNotFoundException;
import ru.spbu.distolymp.exception.common.TechnicalException;
import ru.spbu.distolymp.mapper.admin.directories.towns.TownDetailsMapper;
import ru.spbu.distolymp.repository.geography.TownRepository;
import ru.spbu.distolymp.service.crud.api.geography.CountryCrudService;
import ru.spbu.distolymp.service.crud.api.geography.RegionCrudService;
import ru.spbu.distolymp.service.crud.api.geography.TownCrudService;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Vladislav Konovalov, Maxim Andreev
 */
@Log4j
@Service
@RequiredArgsConstructor
public class TownCrudServiceImpl implements TownCrudService {

    private final TownRepository townRepository;
   
    private final TownDetailsMapper townDetailsMapper;
    private final CountryCrudService countryCrudService;
    private final RegionCrudService regionCrudService;

    @Override
    @Transactional(readOnly = true)
    public Town getTownById(Long id) {
        if (id == null) return null;
        return townRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Town with id=" + id + " was not found"));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TownDto> getTowns(Sort sort) {
        try {
            return townRepository.findAllBy(sort)
                    .stream()
                    .map(Town::toDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            log.error("An error occurred while getting towns", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TownDto> getTowns(Pageable pageable) {
        try {
            return townRepository.findAllBy(pageable)
                    .stream()
                    .map(Town::toDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            log.error("An error occurred while getting towns", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TownDetailsDto getTownDetailsById(Long id) {
        try {
            Town town = townRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            return townDetailsMapper.toDto(town);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting a town by id=" + id, e);
            throw new TechnicalException();
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    @Transactional
    public Town saveOrUpdate(TownDetailsDto townDto) {
        try {
            setForeignRegion(townDto);
            Town town = townDetailsMapper.toEntity(townDto);
            return townRepository.save(town);
        } catch (DataAccessException e) {
            log.error("An error occurred while saving or updating a town", e);
            throw new TechnicalException();
        }
    }

    private void setForeignRegion(TownDetailsDto townDto) {
        Country country = countryCrudService.getCountryByName(townDto.getCountryName())
                .orElseThrow(() -> new IllegalArgumentException("There is no country with name " + townDto.getCountryName()));
        List<RegionNameCodeCountryDto> regionDtoList = regionCrudService.getRegionsByCountryId(country.getId());
        if (regionDtoList.size() == 1)
            townDto.setRegion(regionDtoList.get(0));
    }

    @Override
    @Transactional
    public void deleteTownsByIds(Long[] ids) {
        try {
            townRepository.deleteAllByIdIn(Arrays.asList(ids));
        } catch (DataAccessException e) {
            log.error("An error occurred while deleting towns by ids", e);
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TownDto> getTownsBySpec(Specification<Town> spec, Sort sort) {
        try {
            return townRepository.findAll(spec, sort)
                    .stream()
                    .map(Town::toDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            log.error("An error occurred while getting towns by specs", e);
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TownDto> getTownsBySpec(Specification<Town> spec, Pageable pageable) {
        try {
            return townRepository.findAll(spec, pageable).getContent()
                    .stream()
                    .map(Town::toDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            log.error("An error occurred while getting towns by specs", e);
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Town> getVisibleTownsInRegion(Long regionId) {
        try {
            return townRepository.findByRegionIdAndVisible(regionId, Visible.yes);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting towns by specs", e);
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Town> getTownByRegionIdAndName(Long regionId, String name) {
        try {
            return townRepository.findFirstByRegionIdAndNameIgnoreCase(regionId, name);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting towns by specs", e);
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional
    public Town saveOrUpdate(Town town) {
        try {
            return townRepository.save(town);
        } catch (DataAccessException e) {
            log.error("An error occurred while saving town", e);
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Town> getVisibleTownsInCountryWithNoRegions(Long countryId) {
        try {
            Optional<Country> country = countryCrudService.getCountryById(countryId);
            if (country.isPresent() && !country.get().isRussia()) {
                Region region = country.get().getRegions().get(0);
                return townRepository.getTownsByRegionIdAndVisibleOrderByName(region.getId(), Visible.yes);
            }
            return Collections.emptyList();
        } catch (DataAccessException e) {
            log.error("An error occurred while saving town", e);
            throw new TechnicalException();
        }
    }

}
