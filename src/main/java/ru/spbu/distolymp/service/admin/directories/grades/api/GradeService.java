package ru.spbu.distolymp.service.admin.directories.grades.api;

import org.springframework.ui.ModelMap;

/**
 * @author Vladislav Konovalov
 */
public interface GradeService {

    void fillShowAllGradesModelMap(ModelMap modelMap, Long divisionId);

    void deleteGradeById(Long id, Long divisionId);

}
