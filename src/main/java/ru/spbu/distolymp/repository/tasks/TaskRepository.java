package ru.spbu.distolymp.repository.tasks;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.spbu.distolymp.entity.tasks.Task;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface TaskRepository extends PagingAndSortingRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    List<Task> findByType(Integer type, Sort sort);
    List<Task> findByType(Integer type, Pageable pageable);
    Task findFirstById(Long id);
}
