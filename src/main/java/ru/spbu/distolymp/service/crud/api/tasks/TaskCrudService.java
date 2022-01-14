package ru.spbu.distolymp.service.crud.api.tasks;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskListDto;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface TaskCrudService {
    List<TaskListDto> getTasks(Sort sort);
    List<TaskListDto> getTasks(Pageable pageable);
}
