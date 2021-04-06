package ru.spbu.distolymp.service.admin.directories.grades.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.entity.education.grade.GradeNameDto;
import ru.spbu.distolymp.dto.entity.education.grade.GradeDto;

/**
 * @author Vladislav Konovalov
 */
public interface GradeService {

    void fillShowAllGradesModelMap(ModelMap modelMap);

    void deleteGradeById(Long id);

    void saveNewGrade(GradeNameDto gradeNameDto);

    void renameGrade(GradeNameDto gradeNameDto);

    void fillShowEditPageModelMap(Long id, ModelMap modelMap);

    void updateGrade(GradeDto gradeDto);

}
