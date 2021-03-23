package ru.spbu.distolymp.service.crud.api.lists;

import ru.spbu.distolymp.dto.admin.directories.lists.grades.GradeListDto;
import ru.spbu.distolymp.dto.admin.directories.lists.grades.GradeNameDto;
import ru.spbu.distolymp.dto.entity.lists.GradeEditDto;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface GradeCrudService {

    List<GradeListDto> getShowAllGradesByDivisionId(Long divisionId);

    GradeEditDto getGradeByIdAndDivisionId(Long id, Long divisionId);

    void deleteGradeByIdAndDivisionId(Long id, Long divisionId);

    void saveNewGrade(GradeNameDto gradeNameDto);

    void renameGrade(GradeNameDto gradeNameDto);

    void updateGrade(GradeEditDto gradeEditDto);

}
