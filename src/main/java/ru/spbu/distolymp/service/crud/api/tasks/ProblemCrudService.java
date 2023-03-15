package ru.spbu.distolymp.service.crud.api.tasks;

import ru.spbu.distolymp.dto.entity.tasks.ProblemDto;
import ru.spbu.distolymp.entity.tasks.Problem;

import java.util.List;

public interface ProblemCrudService {
    List<ProblemDto> getAvailableProblems();
    Problem getProblemById(Long id);
}
