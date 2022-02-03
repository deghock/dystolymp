package ru.spbu.distolymp.service.admin.tasks.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskFilter;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskListDto;
import ru.spbu.distolymp.dto.entity.tasks.tasks.TaskDto;

/**
 * @author Vladislav Konovalov
 */
public interface TaskService {
    void fillShowAllTaskModelMap(ModelMap modelMap, int numberTasksDisplayed);
    void fillShowAllTaskByFilterModelMap(TaskFilter taskFilter, ModelMap modelMap);
    void fillShowAddPageModelMap(ModelMap modelMap);
    void fillShowEditPageModelMap(Long id, ModelMap modelMap);
    void fillShowPreviewPageModelMap(Long id, ModelMap modelMap);
    void addTask(TaskDto taskDto);
    void updateTask(TaskDto taskDto);
    void deleteTaskAndImage(Long id);
    void copyTask(TaskListDto taskDto);
    void unarchiveTask(Long id);
}
