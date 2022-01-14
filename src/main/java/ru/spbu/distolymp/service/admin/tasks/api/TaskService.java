package ru.spbu.distolymp.service.admin.tasks.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskFilter;

/**
 * @author Vladislav Konovalov
 */
public interface TaskService {
    void fillShowAllTaskModelMap(ModelMap modelMap, int numberTasksDisplayed);
    void fillShowAllTaskByFilterModelMap(TaskFilter taskFilter, ModelMap modelMap);
}
