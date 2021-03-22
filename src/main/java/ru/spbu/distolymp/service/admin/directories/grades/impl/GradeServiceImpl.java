package ru.spbu.distolymp.service.admin.directories.grades.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.lists.grades.GradeListDto;
import ru.spbu.distolymp.mapper.admin.directories.lists.grades.GradeListMapper;
import ru.spbu.distolymp.repository.lists.GradeRepository;
import ru.spbu.distolymp.service.admin.directories.grades.api.GradeService;
import ru.spbu.distolymp.service.crud.impl.lists.GradeCrudServiceImpl;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Service
public class GradeServiceImpl extends GradeCrudServiceImpl implements GradeService {

    public GradeServiceImpl(GradeListMapper gradeListMapper, GradeRepository gradeRepository) {
        super(gradeListMapper, gradeRepository);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllGradesModelMap(ModelMap modelMap, Long divisionId) {
        List<GradeListDto> grades = getAllGradesByDivisionId(divisionId);
        modelMap.put("grades", grades);
    }

}
