package ru.spbu.distolymp.service.crud.impl.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskListDto;
import ru.spbu.distolymp.dto.entity.tasks.tasks.TaskDto;
import ru.spbu.distolymp.entity.tasks.Task;
import ru.spbu.distolymp.exception.crud.tasks.TaskCrudServiceException;
import ru.spbu.distolymp.mapper.admin.tasks.tasks.TaskListMapper;
import ru.spbu.distolymp.mapper.entity.tasks.TaskMapper;
import ru.spbu.distolymp.repository.tasks.TaskRepository;
import ru.spbu.distolymp.service.crud.api.tasks.TaskCrudService;
import javax.persistence.EntityNotFoundException;
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
    private final TaskMapper taskMapper;

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

    @Override
    @Transactional(readOnly = true)
    public List<TaskListDto> getTasksBySpec(Specification<Task> spec, Pageable pageable) {
        try {
            List<Task> taskList = taskRepository.findAll(spec, pageable).getContent();
            return taskListMapper.toDtoList(taskList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting tasks by specs", e);
            throw new TaskCrudServiceException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskListDto> getTasksBySpec(Specification<Task> spec, Sort sort) {
        try {
            List<Task> taskList = taskRepository.findAll(spec, sort);
            return taskListMapper.toDtoList(taskList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting tasks by specs", e);
            throw new TaskCrudServiceException();
        }
    }

    @Override
    @Transactional
    public void saveOrUpdate(TaskDto taskDto) {
        try {
            Task task = taskMapper.toEntity(taskDto);
            fillMissingFields(task);
            taskRepository.save(task);
        } catch (DataAccessException e) {
            log.error("An error occurred while saving or updating a task", e);
            throw new TaskCrudServiceException();
        }
    }

    private void fillMissingFields(Task task) {
        task.setType(3);
        task.setStatus(1);
        if (task.getWidth() == null)
            task.setWidth(0);
        if (task.getHeight() == null)
            task.setHeight(0);
        if (task.getAnswerNote() != 0 && task.getAnswerNote() != 1)
            task.setCorrectAnswer("answer=0");
        task.setMaxPoints(getPoints(task.getGradePoints()));
    }

    private Double getPoints(String pointsStr) {
        pointsStr = pointsStr.replaceAll("\\s+","");
        pointsStr = pointsStr.replace(",",".");
        String[] pointsList = pointsStr.split(";");
        Double result = 0.0;
        for (String point : pointsList)
            if (!point.equals(""))
                result += Double.parseDouble(point);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDto getTaskById(Long id) {
        try {
            Task task = taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            return taskMapper.toDto(task);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while getting a task by id=" + id, e);
            throw new TaskCrudServiceException();
        }
    }

    @Override
    @Transactional
    public void deleteTaskById(Long id) {
        try {
            Task task = taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            taskRepository.delete(task);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while deleting a task with id=" + id, e);
            throw new TaskCrudServiceException();
        }
    }
}
