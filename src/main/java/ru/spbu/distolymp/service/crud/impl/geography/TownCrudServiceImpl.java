package ru.spbu.distolymp.service.crud.impl.geography;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.directories.countries.CountryNameDto;
import ru.spbu.distolymp.dto.admin.directories.towns.TownDetailsDto;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeCountryDto;
import ru.spbu.distolymp.dto.entity.geography.town.TownDto;
import ru.spbu.distolymp.entity.geography.Town;
import ru.spbu.distolymp.exception.crud.geography.TownCrudServiceException;
import ru.spbu.distolymp.mapper.admin.directories.towns.TownDetailsMapper;
import ru.spbu.distolymp.mapper.entity.geography.TownMapper;
import ru.spbu.distolymp.repository.geography.TownRepository;
import ru.spbu.distolymp.service.crud.api.geography.CountryCrudService;
import ru.spbu.distolymp.service.crud.api.geography.RegionCrudService;
import ru.spbu.distolymp.service.crud.api.geography.TownCrudService;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@RequiredArgsConstructor
public class TownCrudServiceImpl implements TownCrudService {
    protected final TownRepository townRepository;
    private final TownMapper townMapper;
    private final TownDetailsMapper townDetailsMapper;
    protected final CountryCrudService countryCrudServiceImpl;
    protected final RegionCrudService regionCrudService;

    @Override
    @Transactional(readOnly = true)
    public List<TownDto> getTowns(Sort sort) {
        try {
            List<Town> townList = townRepository.findAllBy(sort);
            return townMapper.toDtoList(townList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting towns", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TownDto> getTowns(Pageable pageable) {
        try {
            List<Town> townList = townRepository.findAllBy(pageable);
            return townMapper.toDtoList(townList);
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
        } catch (EntityNotFoundException | DataAccessException e) {
            log.error("An error occurred while getting a town by id=" + id, e);
            throw new TownCrudServiceException();
        }
    }

    @Override
    @Transactional
    public void saveOrUpdate(TownDetailsDto townDto) {
        try {
            setForeignRegion(townDto);
            Town town = townDetailsMapper.toEntity(townDto);
            townRepository.save(town);
        } catch (DataAccessException e) {
            log.error("An error occurred while saving or updating a town", e);
            throw new TownCrudServiceException();
        }
    }

    private void setForeignRegion(TownDetailsDto townDto) {
        CountryNameDto countryDto =
                countryCrudServiceImpl.getCountryByNameOrNull(townDto.getCountryName());
        List<RegionNameCodeCountryDto> regionDtoList =
                regionCrudService.getRegionsByCountryId(countryDto.getId());
        if (regionDtoList.size() == 1)
            townDto.setRegion(regionDtoList.get(0));
    }
}
