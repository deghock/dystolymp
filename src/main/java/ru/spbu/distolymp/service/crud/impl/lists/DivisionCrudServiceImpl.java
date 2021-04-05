package ru.spbu.distolymp.service.crud.impl.lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.lists.DivisionDto;
import ru.spbu.distolymp.entity.lists.Division;
import ru.spbu.distolymp.exception.crud.lists.division.DivisionCrudException;
import ru.spbu.distolymp.mapper.entity.lists.DivisionMapper;
import ru.spbu.distolymp.repository.lists.DivisionRepository;
import ru.spbu.distolymp.service.crud.api.lists.DivisionCrudService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Transactional
    public Division getAnyDivision() {
        Optional<Division> divisionOpt = divisionRepository.findFirstByOrderById();
        return divisionOpt.orElseGet(this::getNewDivision);
    }

    private Division getNewDivision() {
        Division division = new Division();
        division.setName("Default division");
        division.setPrefix(" ");

        try {
            divisionRepository.save(division);
        } catch (DataAccessException e) {
            log.error("An error occurred while saving default division", e);
            throw new DivisionCrudException();
        }

        return division;
    }

}