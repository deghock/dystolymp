package ru.spbu.distolymp.service.crud.impl.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.common.files.FileService;
import ru.spbu.distolymp.dto.admin.tasks.TaskListDto;
import ru.spbu.distolymp.entity.tasks.Problem;
import ru.spbu.distolymp.entity.tasks.Task;
import ru.spbu.distolymp.exception.common.TechnicalException;
import ru.spbu.distolymp.mapper.admin.tasks.api.TaskListMapper;
import ru.spbu.distolymp.mapper.entity.tasks.api.TaskMapper;
import ru.spbu.distolymp.repository.tasks.TaskRepository;
import ru.spbu.distolymp.service.crud.api.lists.ListingProblemCrudService;
import ru.spbu.distolymp.service.crud.api.tasks.TaskCrudService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@Primary
@RequiredArgsConstructor
public class TaskCrudServiceImpl implements TaskCrudService {
    private static final String SAVE_OR_UPDATE_PARAM = "An error occurred while saving or updating a task";
    private final TaskRepository taskRepository;
    private final ListingProblemCrudService listingProblemCrudService;
    private final TaskListMapper taskListMapper;
    protected final TaskMapper taskMapper;
    @Autowired
    @Qualifier("taskFileService")
    protected FileService fileService;

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
            return new ArrayList<>();
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
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void saveOrUpdate(Task task, boolean deleteImage) {
        String imageName = task.getImageFileName();
        if (deleteImage) task.setImageFileName(null);
        try {
            taskRepository.save(task);
            if (deleteImage) fileService.deleteFile(imageName);
        } catch (DataAccessException e) {
            log.error(SAVE_OR_UPDATE_PARAM, e);
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional
    public void saveOrUpdate(Task task, byte[] image) {
        String imageFileName = task.getImageFileName();
        boolean isFileSaved = fileService.saveFile(image, imageFileName);
        if (!isFileSaved)
            throw new TechnicalException("Task image is not saved");
        try {
            taskRepository.save(task);
        } catch (DataAccessException e) {
            log.error(SAVE_OR_UPDATE_PARAM, e);
            fileService.deleteFile(imageFileName);
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
        fileService.deleteFile(oldImage);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Task> getTaskById(Long id) {
        if (id == null) return Optional.empty();
        try {
            return taskRepository.findById(id);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting a task with id=" + id, e);
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional
    public void deleteTaskById(Long id) {
        try {
            Task task = taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            listingProblemCrudService.deleteByProblemId(id);
            taskRepository.delete(task);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while deleting a task with id=" + id, e);
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional
    public Problem copyFromProblem(Long copyId, Problem problem) {
        try{
            Task copyModel = taskRepository.findFirstById(copyId).copyFromProblem(problem);
            taskRepository.save(copyModel);
            return copyModel;
        }catch (DataAccessException e){
            log.error(e);
            throw new TechnicalException();
        }
    }
}
