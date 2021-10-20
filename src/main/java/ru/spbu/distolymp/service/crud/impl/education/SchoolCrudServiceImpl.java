package ru.spbu.distolymp.service.crud.impl.education;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.education.SchoolDto;
import ru.spbu.distolymp.entity.division.Division;
import ru.spbu.distolymp.entity.education.School;
import ru.spbu.distolymp.exception.crud.education.SchoolCrudException;
import ru.spbu.distolymp.mapper.entity.education.SchoolMapper;
import ru.spbu.distolymp.repository.education.SchoolRepository;
import ru.spbu.distolymp.service.crud.api.division.DivisionCrudService;
import ru.spbu.distolymp.service.crud.api.education.SchoolCrudService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Andreev
 */
@Log4j
@Service
@RequiredArgsConstructor
public class SchoolCrudServiceImpl implements SchoolCrudService {

    private final SchoolMapper schoolMapper;
    protected final SchoolRepository schoolRepository;
    private final DivisionCrudService divisionCrudService;

    @Override
    @Transactional(readOnly = true)
    public List<SchoolDto> getAllSchools() {
        List<SchoolDto> schoolDtoList = new ArrayList<>();

        try {
            List<School> schools = schoolRepository.findAll();
            schoolDtoList = schoolMapper.toDtoList(schools);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting all schools", e);
        }

        return schoolDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public SchoolDto getSchoolById(Long id) {
        try {
            School school = schoolRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);
            return schoolMapper.toDto(school);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while getting school by id=" + id, e);
            throw new SchoolCrudException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchoolDto> getSchools(Specification<School> specs) {
        List<School> schools = schoolRepository.findAll(specs);
        return schoolMapper.toDtoList(schools);
    }

    @Override
    @Transactional
    public void deleteSchoolsById(List<Long> idList) {
        try {
            schoolRepository.deleteSchoolsByIdIn(idList);
        } catch (DataAccessException e) {
            log.error("An error occurred while deleting schools by id list", e);
            throw new SchoolCrudException();
        }
    }

    @Override
    @Transactional
    public void save(SchoolDto schoolDto) {
        try {
            School school = schoolMapper.toEntity(schoolDto);
            Division division = divisionCrudService.getAnyDivision();
            school.setDivision(division);
            schoolRepository.save(school);
        } catch (DataAccessException e) {
            log.error("An error occurred while saving school", e);
            throw new SchoolCrudException();
        }
    }


    @Override
    @Transactional
    public void update(SchoolDto schoolDto) {
        try {
            School school = schoolRepository.findById(schoolDto.getId())
                    .orElseThrow(EntityNotFoundException::new);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while updating school with id=" + schoolDto.getId(), e);
            throw new SchoolCrudException();
        }
    }

    @Override
    @Transactional
    public void saveOrUpdate(SchoolDto schoolDto) {
        if (schoolDto.getId() == null || !schoolRepository.findById(schoolDto.getId()).isPresent()) {
            save(schoolDto);
        } else {
            update(schoolDto);
        }
    }
}
