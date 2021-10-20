package ru.spbu.distolymp.service.crud.impl.geography;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.entity.geography.Town;
import ru.spbu.distolymp.repository.geography.TownRepository;
import ru.spbu.distolymp.service.crud.api.geography.TownCrudService;

import javax.persistence.EntityNotFoundException;

/**
 * @author Maxim Andreev
 */
@Log4j
@Service
@RequiredArgsConstructor
public class TownCrudServiceImpl implements TownCrudService {

    private final TownRepository townRepository;

    @Override
    @Transactional(readOnly = true)
    public Town getTownById(Long id) {
        if (id == null) return null;
        return townRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Town with id=" + id + " was not found"));
    }
}
