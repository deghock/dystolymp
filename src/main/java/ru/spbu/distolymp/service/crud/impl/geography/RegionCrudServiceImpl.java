package ru.spbu.distolymp.service.crud.impl.geography;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.geography.region.RegionNameCodeDto;
import ru.spbu.distolymp.repository.geography.RegionRepository;
import ru.spbu.distolymp.service.crud.api.geography.CountryCrudService;
import ru.spbu.distolymp.service.crud.api.geography.RegionCrudService;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@RequiredArgsConstructor
public class RegionCrudServiceImpl implements RegionCrudService {
    private final RegionRepository regionRepository;
    private final CountryCrudService countryCrudServiceImpl;

    @Override
    @Transactional(readOnly = true)
    public List<RegionNameCodeDto> getAllRussianRegions() {
        try {
            Long russiaId = countryCrudServiceImpl.getCountryByNameOrNull("Россия").getId();
            return regionRepository.findRegionsByCountryIdOrderByCode(russiaId);
        } catch (DataAccessException | NullPointerException e) {
            log.error("An error occurred while getting russian regions", e);
            return new ArrayList<>();
        }
    }
}
