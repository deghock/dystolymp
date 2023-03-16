package ru.spbu.distolymp.mapper.admin.tasks.api;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.admin.tasks.TaskListDto;
import ru.spbu.distolymp.entity.tasks.Task;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Mapper
public interface TaskListMapper {
    TaskListDto toDto(Task task);
    List<TaskListDto> toDtoList(List<Task> taskList);

    default String integerToString(Integer status) {
        switch (status) {
            case 1:
                return "Новая";
            case 2:
                return "Опубликована";
            case 3:
                return "Архив";
            default:
                return "Неизвестен";
        }
    }
}
