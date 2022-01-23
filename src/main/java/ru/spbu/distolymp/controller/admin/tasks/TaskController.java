package ru.spbu.distolymp.controller.admin.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskFilter;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskListDto;
import ru.spbu.distolymp.dto.entity.tasks.tasks.TaskDto;
import ru.spbu.distolymp.exception.common.TechnicalException;
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
    private static final int DEFAULT_RESULT_SIZE = 0;
    private static final String LIST_PAGE = ROOT_DIR + "list";
    private static final String TASKS_TABLE = ROOT_DIR + "tasks-table :: #tasks-table";
    private static final String EDIT_PAGE = ROOT_DIR + "edit";
    private static final String REDIRECT_LIST = "redirect:/tasks/list";
    private static final String SUCCESS_PARAM = "success";
    private final TaskService taskService;

    @GetMapping("/list")
    public String getTasks(ModelMap modelMap) {
        taskService.fillShowAllTaskModelMap(modelMap, DEFAULT_RESULT_SIZE);
        return LIST_PAGE;
    }

    @GetMapping("/filter")
    public String getTasksByFilter(TaskFilter taskFilter, ModelMap modelMap) {
        taskService.fillShowAllTaskByFilterModelMap(taskFilter, modelMap);
        return TASKS_TABLE;
    }

    @GetMapping("/add")
    public String getAddPage(ModelMap modelMap) {
        taskService.fillShowAddPageModelMap(modelMap);
        return EDIT_PAGE;
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") Long id, ModelMap modelMap) {
        taskService.fillShowEditPageModelMap(id, modelMap);
        return EDIT_PAGE;
    }

    @PostMapping("/save-or-edit")
    public String saveOrUpdate(@Valid @ModelAttribute("task") TaskDto taskDto,
                               BindingResult bindingResult, RedirectAttributes ra) {
        if (bindingResult.hasErrors())
            return EDIT_PAGE;
        if (taskDto.getId() == null)
            taskService.addTask(taskDto);
        else
            taskService.updateTask(taskDto);
        ra.addFlashAttribute(SUCCESS_PARAM, "Изменения сохранены.");
        return REDIRECT_LIST;
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id, RedirectAttributes ra) {
        taskService.deleteTaskAndImage(id);
        ra.addFlashAttribute(SUCCESS_PARAM, "Задача удалена.");
        return REDIRECT_LIST;
    }

    @PostMapping("/copy")
    public String copyTask(@ModelAttribute("taskForCopy") TaskListDto taskDto,
                           RedirectAttributes ra) {
        taskService.copyTask(taskDto);
        ra.addFlashAttribute(SUCCESS_PARAM, "Задача скопирована.");
        return REDIRECT_LIST;
    }

    @GetMapping("/unarchive/{id}")
    public String unarchiveTask(@PathVariable("id") Long id) {
        taskService.unarchiveTask(id);
        return REDIRECT_LIST;
    }

    @ExceptionHandler(TechnicalException.class)
    public String handleTechnicalException(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "Произошла ошибка. Пожалуйста, попробуйте повторить операцию позже.");
        return REDIRECT_LIST;
    }
}
