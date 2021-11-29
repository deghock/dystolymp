package ru.spbu.distolymp.service.crud.impl.education;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.directories.grades.GradeListDto;
import ru.spbu.distolymp.dto.entity.education.grade.GradeNameDto;
import ru.spbu.distolymp.dto.entity.education.grade.GradeDto;
import ru.spbu.distolymp.entity.division.Division;
import ru.spbu.distolymp.entity.education.Grade;
import ru.spbu.distolymp.exception.crud.education.grade.AddNewGradeException;
import ru.spbu.distolymp.exception.crud.education.grade.DeleteGradeException;
import ru.spbu.distolymp.exception.crud.education.grade.GradeCrudServiceException;
import ru.spbu.distolymp.exception.crud.education.grade.RenameGradeException;
import ru.spbu.distolymp.mapper.admin.directories.lists.grades.GradeListMapper;
import ru.spbu.distolymp.mapper.entity.education.grade.GradeNameMapper;
import ru.spbu.distolymp.mapper.entity.education.grade.GradeMapper;
import ru.spbu.distolymp.repository.education.GradeRepository;
import ru.spbu.distolymp.service.crud.api.division.DivisionCrudService;
import ru.spbu.distolymp.service.crud.api.education.GradeCrudService;

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
    private final GradeMapper gradeMapper;
    protected final GradeRepository gradeRepository;
    private final DivisionCrudService divisionCrudService;

    @Override
    @Transactional(readOnly = true)
    public List<GradeListDto> getAllGrades() {
        try {
            List<Grade> gradeList = gradeRepository.findAllByOrderById();
            return gradeListMapper.toDtoList(gradeList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting all grades", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public GradeDto getGradeById(Long id) {
        try {
            return gradeRepository.findById(id)
                    .map(gradeMapper::toDto)
                    .orElseThrow(EntityNotFoundException::new);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while getting a grade with id=" + id, e);
            throw new GradeCrudServiceException();
        }
    }

    @Override
    @Transactional
    public void deleteGradeById(Long id) {
        try {
            Grade grade = gradeRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);
            gradeRepository.delete(grade);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while deleting a grade with id=" + id, e);
            throw new DeleteGradeException();
        }
    }

    @Override
    @Transactional
    public void saveNewGrade(GradeNameDto gradeNameDto) {
        try {
            Grade grade = gradeNameMapper.toEntity(gradeNameDto);
            Division division = divisionCrudService.getAnyDivision();

            grade.setDivision(division);
            gradeRepository.save(grade);
        } catch (DataAccessException e) {
            log.error("An error occurred while adding a new grade", e);
            throw new AddNewGradeException();
        }
    }

    @Override
    @Transactional
    public void renameGrade(GradeNameDto gradeNameDto) {
        try {
            Long id = gradeNameDto.getId();
            Grade grade = gradeRepository.findById(id).orElseThrow(EntityNotFoundException::new);

            grade.setName(gradeNameDto.getName());
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while renaming a grade with id=" + gradeNameDto.getId(), e);
            throw new RenameGradeException();
        }
    }

    @Override
    @Transactional
    public void updateGrade(GradeDto gradeDto) {
        try {
            Grade grade = gradeMapper.toEntity(gradeDto);
            Division division = divisionCrudService.getAnyDivision();

            grade.setDivision(division);
            gradeRepository.save(grade);
        } catch (DataAccessException e) {
            log.error("An error occurred while updating a grade with id=" + gradeDto.getId(), e);
            throw new GradeCrudServiceException();
        }
    }

    @Override
    @Transactional
    public void updateGrade(GradeListDto gradeDto) {
        try {
            Grade grade = gradeListMapper.toEntity(gradeDto);
            Division division = divisionCrudService.getAnyDivision();

            grade.setDivision(division);
            gradeRepository.save(grade);
        } catch (DataAccessException e) {
            log.error("An error occurred while updating a grade with id=" + gradeDto.getId(), e);
            throw new GradeCrudServiceException();
        }
    }

}
