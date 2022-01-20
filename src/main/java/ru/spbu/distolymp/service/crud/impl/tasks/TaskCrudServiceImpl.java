package ru.spbu.distolymp.service.crud.impl.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.common.files.ImageService;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskListDto;
import ru.spbu.distolymp.dto.entity.tasks.tasks.TaskDto;
import ru.spbu.distolymp.entity.tasks.Task;
import ru.spbu.distolymp.exception.common.ResourceNotFoundException;
import ru.spbu.distolymp.exception.common.TechnicalException;
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
    protected final TaskMapper taskMapper;
    @Autowired
    @Qualifier("taskImageService")
    protected ImageService imageService;

    private static final String SAVE_OR_UPDATE_PARAM = "An error occurred while saving or updating a task";

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
            throw new TechnicalException();
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
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional
    public void saveOrUpdate(Task task, boolean deleteImage) {
        String imageName = task.getImageFileName();
        if (deleteImage) task.setImageFileName(null);
        try {
            taskRepository.save(task);
            if (deleteImage) imageService.deleteImage(imageName);
        } catch (DataAccessException e) {
            log.error(SAVE_OR_UPDATE_PARAM, e);
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional
    public void saveOrUpdate(Task task, byte[] image) {
        String imageFileName = task.getImageFileName();
        boolean isFileSaved = imageService.saveImage(image, imageFileName);
        if (!isFileSaved)
            throw new TechnicalException("Task image is not saved");
        try {
            taskRepository.save(task);
        } catch (DataAccessException e) {
            log.error(SAVE_OR_UPDATE_PARAM, e);
            imageService.deleteImage(imageFileName);
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional
    public void saveOrUpdate(Task task, byte[] image, String oldImage, boolean deleteImage) {
        if (deleteImage) {
            task.setImageFileName(null);
            saveOrUpdate(task, false);
        } else {
            saveOrUpdate(task, image);
        }
        imageService.deleteImage(oldImage);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDto getTaskById(Long id) {
        try {
            Task task = taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            return taskMapper.toDto(task);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting a task by id=" + id, e);
            throw new TechnicalException();
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException();
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
            throw new TechnicalException();
        }
    }
}
