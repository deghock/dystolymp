package ru.spbu.distolymp.service.admin.tasks.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskFilter;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskListDto;
import ru.spbu.distolymp.entity.tasks.Task;
import ru.spbu.distolymp.mapper.admin.tasks.tasks.TaskListMapper;
import ru.spbu.distolymp.repository.tasks.TaskRepository;
import ru.spbu.distolymp.service.admin.tasks.api.TaskService;
import ru.spbu.distolymp.service.crud.impl.tasks.TaskCrudServiceImpl;
import ru.spbu.distolymp.util.admin.tasks.tasks.TaskSpecsConverter;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Service
public class TaskServiceImpl extends TaskCrudServiceImpl implements TaskService {
    private static final Sort SORT_BY_TITLE_ASC = Sort.by("title").ascending();
    private static final String TASKS_PARAM = "taskList";

    public TaskServiceImpl(TaskRepository taskRepository, TaskListMapper taskListMapper) {
        super(taskRepository, taskListMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllTaskModelMap(ModelMap modelMap, int numberTasksDisplayed) {
        List<TaskListDto> taskList = getTasks(numberTasksDisplayed);
        modelMap.put(TASKS_PARAM, taskList);
    }

    private List<TaskListDto> getTasks(int numberTasksDisplayed) {
        if (numberTasksDisplayed <= 0) {
            return getTasks(SORT_BY_TITLE_ASC);
        }
        Pageable pageable = getPageableSortedByTitle(numberTasksDisplayed);
        return getTasks(pageable);
    }

    private Pageable getPageableSortedByTitle(int numberTasksDisplayed) {
        return PageRequest.of(0, numberTasksDisplayed, SORT_BY_TITLE_ASC);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllTaskByFilterModelMap(TaskFilter taskFilter, ModelMap modelMap) {
        int resultSize = taskFilter.getResultSize();
        Specification<Task> specs = TaskSpecsConverter.toSpecs(taskFilter);
        List<TaskListDto> taskDtoList;
        if (specs == null) {
            taskDtoList = getTasks(resultSize);
            modelMap.put(TASKS_PARAM, taskDtoList);
            return;
        }
        if (resultSize == 0) {
            taskDtoList = getTasksBySpec(specs, SORT_BY_TITLE_ASC);
            modelMap.put(TASKS_PARAM, taskDtoList);
            return;
        }
        Pageable sortedByTitleAsc = getPageableSortedByTitle(resultSize);
        taskDtoList = getTasksBySpec(specs, sortedByTitleAsc);
        modelMap.put(TASKS_PARAM, taskDtoList);
    }
}
