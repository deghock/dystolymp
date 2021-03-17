package ru.spbu.distolymp.service.crud.impl.lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.lists.DivisionDto;
import ru.spbu.distolymp.entity.lists.Division;
import ru.spbu.distolymp.mapper.entity.lists.DivisionMapper;
import ru.spbu.distolymp.repository.lists.DivisionRepository;
import ru.spbu.distolymp.service.crud.api.lists.DivisionCrudService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daria Usova
 */
@Log4j
@Service
@RequiredArgsConstructor
public class DivisionCrudServiceImpl implements DivisionCrudService {

    private final DivisionRepository divisionRepository;
    private final DivisionMapper divisionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DivisionDto> getDivisionList() {
        try {
            List<Division> divisionList = (List<Division>) divisionRepository.findAll();
            return divisionMapper.toDtoList(divisionList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting all divisions", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Division getDivisionById(Long id) {
        return divisionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Division with id=" + id + " was not found"));
    }

}
