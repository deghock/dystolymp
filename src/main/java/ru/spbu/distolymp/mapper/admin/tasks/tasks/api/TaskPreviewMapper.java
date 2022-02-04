package ru.spbu.distolymp.mapper.admin.tasks.tasks.api;

import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskViewDto;
import ru.spbu.distolymp.entity.tasks.Task;

/**
 * @author Vladislav Konovalov
 */
public interface TaskPreviewMapper {
    TaskViewDto toDto(Task task);
}
