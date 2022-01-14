package ru.spbu.distolymp.controller.admin.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskFilter;
import ru.spbu.distolymp.exception.crud.tasks.TaskCrudServiceException;
import ru.spbu.distolymp.service.admin.tasks.api.TaskService;

/**
 * @author Vladislav Konovalov
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private static final String ROOT_DIR = "admin/tasks/";
    private static final int DEFAULT_RESULT_SIZE = 20;
    private static final String LIST_PAGE = ROOT_DIR + "list";
    private static final String TASKS_TABLE = ROOT_DIR + "tasks-table :: #tasks-table";
    private static final String PAGE_404 = "exception/404";
    private final TaskService taskService;

    @GetMapping("/list")
    public String getTasks(ModelMap modelMap) {
        taskService.fillShowAllTaskModelMap(modelMap, DEFAULT_RESULT_SIZE);
        return LIST_PAGE;
    }

    @GetMapping("filter")
    public String getTasksByFilter(TaskFilter taskFilter, ModelMap modelMap) {
        taskService.fillShowAllTaskByFilterModelMap(taskFilter, modelMap);
        return TASKS_TABLE;
    }

    @ExceptionHandler(TaskCrudServiceException.class)
    public String handleTaskCrudServiceException() {
        return PAGE_404;
    }
}
