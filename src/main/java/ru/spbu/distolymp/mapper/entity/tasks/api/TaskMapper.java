package ru.spbu.distolymp.mapper.entity.tasks.api;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.tasks.TaskDto;
import ru.spbu.distolymp.entity.tasks.Task;

/**
 * @author Vladislav Konovalov
 */
@Mapper
public interface TaskMapper {
    TaskDto toDto(Task task);
    Task toEntity(TaskDto taskDto);
}
