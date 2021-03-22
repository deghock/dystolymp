package ru.spbu.distolymp.service.crud.impl.lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.directories.lists.grades.GradeListDto;
import ru.spbu.distolymp.entity.lists.Grade;
import ru.spbu.distolymp.mapper.admin.directories.lists.grades.GradeListMapper;
import ru.spbu.distolymp.repository.lists.GradeRepository;
import ru.spbu.distolymp.service.crud.api.lists.GradeCrudService;

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
}
