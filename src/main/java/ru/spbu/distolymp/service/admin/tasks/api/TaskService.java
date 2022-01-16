package ru.spbu.distolymp.service.admin.tasks.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskFilter;
import ru.spbu.distolymp.dto.entity.tasks.tasks.TaskDto;

/**
 * @author Vladislav Konovalov
 */
public interface TaskService {
    void fillShowAllTaskModelMap(ModelMap modelMap, int numberTasksDisplayed);
    void fillShowAllTaskByFilterModelMap(TaskFilter taskFilter, ModelMap modelMap);
    void fillShowEditPageModelMap(ModelMap modelMap);
    void saveOrUpdate(TaskDto taskDto);
}
