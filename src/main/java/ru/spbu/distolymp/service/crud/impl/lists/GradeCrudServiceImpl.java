package ru.spbu.distolymp.service.crud.impl.lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.directories.grades.GradeListDto;
import ru.spbu.distolymp.dto.admin.directories.grades.GradeNameDto;
import ru.spbu.distolymp.dto.entity.lists.GradeEditDto;
import ru.spbu.distolymp.entity.lists.Grade;
import ru.spbu.distolymp.exception.crud.lists.grade.AddNewGradeException;
import ru.spbu.distolymp.exception.crud.lists.grade.GradeCrudServiceException;
import ru.spbu.distolymp.exception.crud.lists.grade.RenameGradeException;
import ru.spbu.distolymp.mapper.admin.directories.lists.grades.GradeListMapper;
import ru.spbu.distolymp.mapper.admin.directories.lists.grades.GradeNameMapper;
import ru.spbu.distolymp.mapper.entity.lists.GradeEditMapper;
import ru.spbu.distolymp.repository.lists.GradeRepository;
import ru.spbu.distolymp.service.crud.api.lists.GradeCrudService;

import javax.persistence.EntityNotFoundException;
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
    private final GradeEditMapper gradeEditMapper;
    protected final GradeRepository gradeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GradeListDto> getAllGradesByDivisionId(Long divisionId) {
        List<GradeListDto> gradeDtoList;
        try {
            List<Grade> gradeList = gradeRepository.findAllByDivisionIdOrderById(divisionId);
            gradeDtoList = gradeListMapper.toDtoList(gradeList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting all grades", e);
            throw new GradeCrudServiceException();
        }
        return gradeDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public GradeEditDto getGradeByIdAndDivisionId(Long id, Long divisionId) {
        try {
            Grade grade = gradeRepository.findByIdAndDivisionId(id, divisionId);
            if (grade == null) {
                throw new EntityNotFoundException("Grade with id=" + id + " not found");
            }
            return gradeEditMapper.toDto(grade);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while getting a grade with id=" + id, e);
            throw new GradeCrudServiceException();
        }
    }

    @Override
    @Transactional
    public void deleteGradeByIdAndDivisionId(Long id, Long divisionId) {
        try {
            gradeRepository.deleteGradeByIdAndDivisionId(id, divisionId);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while deleting a grade with id=" + id, e);
            throw new GradeCrudServiceException();
        }
    }

    @Override
    @Transactional
    public void saveNewGrade(GradeNameDto gradeNameDto) {
        try {
            Grade grade = gradeNameMapper.toEntity(gradeNameDto);
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
            Grade grade = gradeRepository.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("Grade with id=" + id + " not found"));
            grade.setName(gradeNameDto.getName());
            gradeRepository.save(grade);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while renaming a grade with id="
                    + gradeNameDto.getId(), e);
            throw new RenameGradeException();
        }
    }

    @Override
    @Transactional
    public void updateGrade(GradeEditDto gradeEditDto) {
        try {
            Grade grade = gradeEditMapper.toEntity(gradeEditDto);
            gradeRepository.save(grade);
        } catch (DataAccessException e) {
            log.error("An error occurred while updating a grade with id=" +
                    gradeEditDto.getId(), e);
            throw new GradeCrudServiceException();
        }
    }

}
