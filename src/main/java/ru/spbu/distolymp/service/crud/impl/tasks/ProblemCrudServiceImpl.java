package ru.spbu.distolymp.service.crud.impl.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.spbu.distolymp.dto.entity.tasks.ProblemDto;
import ru.spbu.distolymp.entity.tasks.Problem;
import ru.spbu.distolymp.mapper.entity.tasks.api.ProblemMapper;
import ru.spbu.distolymp.repository.tasks.ProblemRepository;
import ru.spbu.distolymp.service.crud.api.tasks.ProblemCrudService;

import java.util.List;

@Log4j
@Service
@RequiredArgsConstructor
public class ProblemCrudServiceImpl implements ProblemCrudService {
    private final ProblemRepository problemRepository;
    private final ProblemMapper problemMapper;

    @Override
    public List<ProblemDto> getAvailableProblems() {
        List<Problem> problems = (List<Problem>) problemRepository.findAll();
        return problemMapper.toDtoList(problems);
    }
}
