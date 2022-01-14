package ru.spbu.distolymp.service.admin.tasks.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskListDto;
import ru.spbu.distolymp.mapper.admin.tasks.tasks.TaskListMapper;
import ru.spbu.distolymp.repository.tasks.TaskRepository;
import ru.spbu.distolymp.service.admin.tasks.api.TaskService;
import ru.spbu.distolymp.service.crud.impl.tasks.TaskCrudServiceImpl;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Service
public class TaskServiceImpl extends TaskCrudServiceImpl implements TaskService {
    private static final Sort SORT_BY_TITLE_ASC = Sort.by("title").ascending();

    public TaskServiceImpl(TaskRepository taskRepository, TaskListMapper taskListMapper) {
        super(taskRepository, taskListMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllTaskModelMap(ModelMap modelMap, int numberTasksDisplayed) {
        List<TaskListDto> taskList = getTasks(numberTasksDisplayed);
        modelMap.put("taskList", taskList);
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
}
