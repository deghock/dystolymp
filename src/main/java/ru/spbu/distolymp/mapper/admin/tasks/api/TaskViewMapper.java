package ru.spbu.distolymp.mapper.admin.tasks.api;

import ru.spbu.distolymp.dto.admin.tasks.TaskViewDto;
import ru.spbu.distolymp.entity.tasks.Task;

/**
 * @author Vladislav Konovalov
 */
public interface TaskViewMapper {
    TaskViewDto toDto(Task task);
    TaskViewDto toDto(Task task, String params);
}
