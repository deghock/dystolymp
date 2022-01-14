package ru.spbu.distolymp.service.admin.tasks.api;

import org.springframework.ui.ModelMap;

/**
 * @author Vladislav Konovalov
 */
public interface TaskService {
    void fillShowAllTaskModelMap(ModelMap modelMap, int numberTasksDisplayed);
}
