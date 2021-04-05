package ru.spbu.distolymp.service.crud.api.education;

import ru.spbu.distolymp.dto.admin.directories.grades.GradeListDto;
import ru.spbu.distolymp.dto.entity.education.grade.GradeNameDto;
import ru.spbu.distolymp.dto.entity.education.grade.GradeDto;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface GradeCrudService {

    List<GradeListDto> getAllGrades();

    GradeDto getGradeByIdAndDivisionId(Long id, Long divisionId);

    void deleteGradeByIdAndDivisionId(Long id, Long divisionId);

    void saveNewGrade(GradeNameDto gradeNameDto);

    void renameGrade(GradeNameDto gradeNameDto);

    void updateGrade(GradeDto gradeDto);

}
