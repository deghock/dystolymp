package ru.spbu.distolymp.service.crud.api.tasks;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.tasks.TaskListDto;
import ru.spbu.distolymp.entity.tasks.Problem;
import ru.spbu.distolymp.entity.tasks.Task;
import java.util.List;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
public interface TaskCrudService {
    List<TaskListDto> getTasks(Sort sort);
    List<TaskListDto> getTasks(Pageable pageable);
    List<TaskListDto> getTasksBySpec(Specification<Task> spec, Pageable pageable);
    List<TaskListDto> getTasksBySpec(Specification<Task> spec, Sort sort);
    void saveOrUpdate(Task task, boolean deleteImage);
    void saveOrUpdate(Task task, byte[] image);
    void saveOrUpdate(Task task, byte[] image, String oldImage, boolean deleteImage);
    Optional<Task> getTaskById(Long id);
    void deleteTaskById(Long id);
    Problem copyFromProblem(Long copyId, Problem problem);
}
