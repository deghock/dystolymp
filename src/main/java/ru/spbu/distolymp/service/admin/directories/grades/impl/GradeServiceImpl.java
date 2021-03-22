package ru.spbu.distolymp.service.admin.directories.grades.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.lists.grades.GradeListDto;
import ru.spbu.distolymp.dto.admin.directories.lists.grades.GradeNameDto;
import ru.spbu.distolymp.mapper.admin.directories.lists.grades.GradeListMapper;
import ru.spbu.distolymp.mapper.admin.directories.lists.grades.GradeNameMapper;
import ru.spbu.distolymp.repository.lists.GradeRepository;
import ru.spbu.distolymp.service.admin.directories.grades.api.GradeService;
import ru.spbu.distolymp.service.crud.api.lists.DivisionCrudService;
import ru.spbu.distolymp.service.crud.impl.lists.GradeCrudServiceImpl;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Service
public class GradeServiceImpl extends GradeCrudServiceImpl implements GradeService {

    public GradeServiceImpl(GradeListMapper gradeListMapper, GradeNameMapper gradeNameMapper, DivisionCrudService divisionCrudService, GradeRepository gradeRepository) {
        super(gradeListMapper, gradeNameMapper, divisionCrudService, gradeRepository);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllGradesModelMap(ModelMap modelMap, Long divisionId) {
        GradeNameDto newGrade = new GradeNameDto();
        newGrade.setDivisionId(divisionId);
        List<GradeListDto> grades = getAllGradesByDivisionId(divisionId);
        modelMap.put("grade", newGrade);
        modelMap.put("grades", grades);
    }

    @Override
    @Transactional
    public void deleteGradeById(Long id, Long divisionId) {
        deleteGradeByIdAndDivisionId(id, divisionId);
    }

    @Override
    @Transactional
    public void addNewGrade(GradeNameDto gradeNameDto) {
        saveNewGrade(gradeNameDto);
    }

}
