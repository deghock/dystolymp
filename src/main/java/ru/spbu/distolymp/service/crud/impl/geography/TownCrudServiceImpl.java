package ru.spbu.distolymp.service.crud.impl.geography;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.geography.town.TownDto;
import ru.spbu.distolymp.entity.geography.Town;
import ru.spbu.distolymp.mapper.entity.geography.TownMapper;
import ru.spbu.distolymp.repository.geography.TownRepository;
import ru.spbu.distolymp.service.crud.api.geography.TownCrudService;

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

}
