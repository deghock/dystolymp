package ru.spbu.distolymp.service.crud.impl.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskListDto;
import ru.spbu.distolymp.entity.tasks.Task;
import ru.spbu.distolymp.mapper.admin.tasks.tasks.TaskListMapper;
import ru.spbu.distolymp.repository.tasks.TaskRepository;
import ru.spbu.distolymp.service.crud.api.tasks.TaskCrudService;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@RequiredArgsConstructor
public class TaskCrudServiceImpl implements TaskCrudService {
    private final TaskRepository taskRepository;
    private final TaskListMapper taskListMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TaskListDto> getTasks(Sort sort) {
        try {
            List<Task> taskList = taskRepository.findByType(3, sort);
            return taskListMapper.toDtoList(taskList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting tasks", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskListDto> getTasks(Pageable pageable) {
        try {
            List<Task> taskList = taskRepository.findByType(3, pageable);
            return taskListMapper.toDtoList(taskList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting tasks", e);
            return new ArrayList<>();
        }
    }
}
