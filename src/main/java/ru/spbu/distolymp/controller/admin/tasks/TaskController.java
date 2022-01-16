package ru.spbu.distolymp.controller.admin.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskFilter;
import ru.spbu.distolymp.dto.entity.tasks.tasks.TaskDto;
import ru.spbu.distolymp.exception.crud.tasks.TaskCrudServiceException;
import ru.spbu.distolymp.service.admin.tasks.api.TaskService;
import javax.validation.Valid;

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
    private static final String EDIT_PAGE = ROOT_DIR + "edit";
    private static final String REDIRECT_LIST = "redirect:/tasks/list/";
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

    @GetMapping("/add")
    public String getAddPage(ModelMap modelMap) {
        taskService.fillShowEditPageModelMap(modelMap);
        return EDIT_PAGE;
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") Long id, ModelMap modelMap) {
        taskService.fillShowEditPageModelMap(id, modelMap);
        return EDIT_PAGE;
    }

    @PostMapping("/save-or-edit")
    public String saveOrUpdate(@Valid @ModelAttribute("task") TaskDto taskDto,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return EDIT_PAGE;
        taskService.saveOrUpdate(taskDto);
        return REDIRECT_LIST;
    }

    @ExceptionHandler(TaskCrudServiceException.class)
    public String handleTaskCrudServiceException() {
        return PAGE_404;
    }
}
