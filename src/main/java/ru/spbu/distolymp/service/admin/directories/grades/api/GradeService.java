package ru.spbu.distolymp.service.admin.directories.grades.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.lists.grades.GradeNameDto;
import ru.spbu.distolymp.dto.entity.lists.GradeEditDto;

/**
 * @author Vladislav Konovalov
 */
public interface GradeService {

    void fillShowAllGradesModelMap(ModelMap modelMap, Long divisionId);

    void deleteGradeById(Long id, Long divisionId);

    void addNewGrade(GradeNameDto gradeNameDto);

    void renameGrade(GradeNameDto gradeNameDto);

    void fillAllGradesByIdModelMap(Long id, Long divisionId, ModelMap modelMap);

    void updateGrade(GradeEditDto gradeEditDto);

}
