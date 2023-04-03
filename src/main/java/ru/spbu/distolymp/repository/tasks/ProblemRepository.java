package ru.spbu.distolymp.repository.tasks;

import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.tasks.Problem;

public interface ProblemRepository extends CrudRepository<Problem, Long> {
}
