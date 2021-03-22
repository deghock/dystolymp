package ru.spbu.distolymp.service.crud.api.lists;

import ru.spbu.distolymp.dto.admin.directories.lists.grades.GradeListDto;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface GradeCrudService {

    List<GradeListDto> getAllGradesByDivisionId(Long divisionId);

}
