package ru.spbu.distolymp.service.admin.directories.grades.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.entity.education.grade.GradeNameDto;
import ru.spbu.distolymp.dto.entity.education.grade.GradeDto;

/**
 * @author Vladislav Konovalov
 */
public interface GradeService {

    void fillShowAllGradesModelMap(ModelMap modelMap);

    void deleteGradeByIdAndDivisionId(Long id, Long divisionId);

    void saveNewGrade(GradeNameDto gradeNameDto);

    void renameGrade(GradeNameDto gradeNameDto);

    void fillShowEditPageModelMap(Long id, Long divisionId, ModelMap modelMap);

    void updateGrade(GradeDto gradeDto);

}
