package ru.spbu.distolymp.service.crud.api.tasks;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskListDto;
import ru.spbu.distolymp.dto.entity.tasks.tasks.TaskDto;
import ru.spbu.distolymp.entity.tasks.Task;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface TaskCrudService {
    List<TaskListDto> getTasks(Sort sort);
    List<TaskListDto> getTasks(Pageable pageable);
    List<TaskListDto> getTasksBySpec(Specification<Task> spec, Pageable pageable);
    List<TaskListDto> getTasksBySpec(Specification<Task> spec, Sort sort);
    void saveOrUpdate(TaskDto taskDto);
    TaskDto getTaskById(Long id);
    void deleteTaskById(Long id);
}
