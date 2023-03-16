package ru.spbu.distolymp.service.crud.impl.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.exception.DataException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.tasks.ProblemDto;
import ru.spbu.distolymp.entity.tasks.Problem;
import ru.spbu.distolymp.mapper.entity.tasks.api.ProblemMapper;
import ru.spbu.distolymp.repository.tasks.ProblemRepository;
import ru.spbu.distolymp.service.crud.api.tasks.ProblemCrudService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Log4j
@Service
@RequiredArgsConstructor
public class ProblemCrudServiceImpl implements ProblemCrudService {
    private final ProblemRepository problemRepository;
    private final ProblemMapper problemMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProblemDto> getAvailableProblems() {
        List<Problem> problems = (List<Problem>) problemRepository.findAll();
        return problemMapper.toDtoList(problems);
    }

    @Override
    @Transactional
    public Problem getProblemById(Long id) {
        try {
            return problemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while getting listing with id=" + id, e);
            throw e;
        }
    }

    @Override
    @Transactional
    public Problem copyProblem(Problem copyProblem, String prefix) {
        try{
            Problem newProblem = copyProblem.copyFrom();
            newProblem.setTitle(prefix + " " + copyProblem.getTitle());
            problemRepository.save(newProblem);
            return newProblem;
        }catch (DataException | EntityNotFoundException e){
            log.error("An error occurred while copying problem ", e);
            throw e;
        }
    }


}
