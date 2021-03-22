package ru.spbu.distolymp.service.crud.impl.lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.directories.lists.grades.GradeListDto;
import ru.spbu.distolymp.dto.admin.directories.lists.grades.GradeNameDto;
import ru.spbu.distolymp.entity.lists.Division;
import ru.spbu.distolymp.entity.lists.Grade;
import ru.spbu.distolymp.exception.crud.lists.grade.AddNewGradeException;
import ru.spbu.distolymp.exception.crud.lists.grade.GradeCrudServiceException;
import ru.spbu.distolymp.mapper.admin.directories.lists.grades.GradeListMapper;
import ru.spbu.distolymp.mapper.admin.directories.lists.grades.GradeNameMapper;
import ru.spbu.distolymp.repository.lists.GradeRepository;
import ru.spbu.distolymp.service.crud.api.lists.DivisionCrudService;
import ru.spbu.distolymp.service.crud.api.lists.GradeCrudService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@RequiredArgsConstructor
public class GradeCrudServiceImpl implements GradeCrudService {

    private final GradeListMapper gradeListMapper;
    private final GradeNameMapper gradeNameMapper;
    private final DivisionCrudService divisionCrudService;
    protected final GradeRepository gradeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GradeListDto> getAllGradesByDivisionId(Long divisionId) {
        List<GradeListDto> gradeListDtoList = new ArrayList<>();
        try {
            List<Grade> grades = gradeRepository.findAllByDivisionId(divisionId);
            gradeListDtoList = gradeListMapper.toDtoList(grades);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting all grades", e);
        }
        return gradeListDtoList;
    }

    @Override
    @Transactional
    public void deleteGradeByIdAndDivisionId(Long id, Long divisionId) {
        try {
            gradeRepository.deleteGradeByIdAndDivisionId(id, divisionId);
        } catch (DataAccessException e) {
            log.error("An error occurred while deleting grades by id", e);
            throw new GradeCrudServiceException();
        }
    }

    @Override
    @Transactional
    public void saveNewGrade(GradeNameDto gradeNameDto) {
        try {
            tryToAddNewGrade(gradeNameDto);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while adding a new grade", e);
            throw new AddNewGradeException();
        }
    }

    private void tryToAddNewGrade(GradeNameDto gradeNameDto) {
        Grade grade = gradeNameMapper.toEntity(gradeNameDto);
        Division division = divisionCrudService.getDivisionById(gradeNameDto.getDivisionId());
        grade.setDivision(division);
        gradeRepository.save(grade);
    }

}
