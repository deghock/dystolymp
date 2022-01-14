package ru.spbu.distolymp.controller.admin.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private final TaskService taskService;

    @GetMapping("/list")
    public String getTasks(ModelMap modelMap) {
        taskService.fillShowAllTaskModelMap(modelMap, DEFAULT_RESULT_SIZE);
        return LIST_PAGE;
    }
}
