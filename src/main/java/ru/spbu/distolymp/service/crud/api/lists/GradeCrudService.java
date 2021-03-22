package ru.spbu.distolymp.service.crud.api.lists;

import ru.spbu.distolymp.dto.admin.directories.lists.grades.GradeListDto;
import ru.spbu.distolymp.dto.admin.directories.lists.grades.GradeNameDto;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface GradeCrudService {

    List<GradeListDto> getAllGradesByDivisionId(Long divisionId);

    void deleteGradeByIdAndDivisionId(Long id, Long divisionId);

    void saveNewGrade(GradeNameDto gradeNameDto);

    void renameGrade(GradeNameDto gradeNameDto);

}
