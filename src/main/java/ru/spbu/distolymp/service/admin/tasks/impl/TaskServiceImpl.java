package ru.spbu.distolymp.service.admin.tasks.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.common.files.FileNameGenerator;
import ru.spbu.distolymp.common.files.FilesUtils;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskFilter;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskListDto;
import ru.spbu.distolymp.dto.entity.tasks.tasks.TaskDto;
import ru.spbu.distolymp.entity.tasks.Task;
import ru.spbu.distolymp.exception.common.ResourceNotFoundException;
import ru.spbu.distolymp.mapper.admin.tasks.tasks.TaskListMapper;
import ru.spbu.distolymp.mapper.entity.tasks.TaskMapper;
import ru.spbu.distolymp.repository.tasks.TaskRepository;
import ru.spbu.distolymp.service.admin.tasks.api.TaskService;
import ru.spbu.distolymp.service.crud.api.lists.ListingProblemCrudService;
import ru.spbu.distolymp.service.crud.impl.tasks.TaskCrudServiceImpl;
import ru.spbu.distolymp.util.admin.tasks.tasks.TaskSpecsConverter;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Service
public class TaskServiceImpl extends TaskCrudServiceImpl implements TaskService {
    private static final Sort SORT_BY_ID_DESC = Sort.by("id").descending();
    private static final String TASKS_PARAM = "taskList";

    public TaskServiceImpl(TaskRepository taskRepository,
                           ListingProblemCrudService listingProblemCrudService,
                           TaskListMapper taskListMapper,
                           TaskMapper taskMapper) {
        super(taskRepository, listingProblemCrudService, taskListMapper, taskMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllTaskModelMap(ModelMap modelMap, int numberTasksDisplayed) {
        List<TaskListDto> taskList = getTasks(numberTasksDisplayed);
        modelMap.put(TASKS_PARAM, taskList);
        modelMap.put("taskForCopy", new TaskListDto());
    }

    private List<TaskListDto> getTasks(int numberTasksDisplayed) {
        if (numberTasksDisplayed <= 0) {
            return getTasks(SORT_BY_ID_DESC);
        }
        Pageable pageable = getPageableSortedById(numberTasksDisplayed);
        return getTasks(pageable);
    }

    private Pageable getPageableSortedById(int numberTasksDisplayed) {
        return PageRequest.of(0, numberTasksDisplayed, SORT_BY_ID_DESC);
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
            taskDtoList = getTasksBySpec(specs, SORT_BY_ID_DESC);
            modelMap.put(TASKS_PARAM, taskDtoList);
            return;
        }
        Pageable sortedByIdDesc = getPageableSortedById(resultSize);
        taskDtoList = getTasksBySpec(specs, sortedByIdDesc);
        modelMap.put(TASKS_PARAM, taskDtoList);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAddPageModelMap(ModelMap modelMap) {
        TaskDto taskDto = new TaskDto();
        taskDto.setAnswerNote(2);
        taskDto.setWidth(0);
        taskDto.setHeight(0);
        modelMap.put("task", taskDto);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowEditPageModelMap(Long id, ModelMap modelMap) {
        TaskDto taskDto = getTaskById(id)
                .map(taskMapper::toDto)
                .orElseThrow(ResourceNotFoundException::new);
        modelMap.put("task", taskDto);
    }

    @Override
    @Transactional
    public void addTask(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        MultipartFile image = taskDto.getImage();
        task.setStatus(1);
        initFields(task);
        if ("".equals(image.getOriginalFilename())) {
            saveOrUpdate(task, false);
        } else {
            String imageExtension = FilesUtils.getImageExtension(image);
            task.setImageFileName(FileNameGenerator.generateFileName(imageExtension));
            saveOrUpdate(task, FilesUtils.getImageBytes(image));
        }
    }

    @Override
    @Transactional
    public void updateTask(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        initFields(task);
        MultipartFile image = taskDto.getImage();
        String oldImageName = task.getImageFileName();
        if (oldImageName == null) {
            if ("".equals(image.getOriginalFilename())) {
                saveOrUpdate(task, false);
            } else {
                String imageExtension = FilesUtils.getImageExtension(image);
                task.setImageFileName(FileNameGenerator.generateFileName(imageExtension));
                saveOrUpdate(task, FilesUtils.getImageBytes(image));
            }
        } else {
            if ("".equals(image.getOriginalFilename())) {
                saveOrUpdate(task, taskDto.isDeleteImage());
            } else {
                String imageExtension = FilesUtils.getImageExtension(image);
                task.setImageFileName(FileNameGenerator.generateFileName(imageExtension));
                saveOrUpdate(task, FilesUtils.getImageBytes(image), oldImageName, taskDto.isDeleteImage());
            }
        }
    }

    private void initFields(Task task) {
        task.setType(3);
        if (task.getWidth() == null)
            task.setWidth(0);
        if (task.getHeight() == null)
            task.setHeight(0);
        if (task.getAnswerNote() != 0 && task.getAnswerNote() != 1)
            task.setCorrectAnswer("answer=0");
        task.setMaxPoints(parseAndEvalPoints(task.getGradePoints()));
    }

    private Double parseAndEvalPoints(String pointsStr) {
        pointsStr = pointsStr.replaceAll("\\s+","");
        String[] pointsList = pointsStr.split(";");
        Double result = 0.0;
        for (String point : pointsList)
            if (!point.equals(""))
                result += Double.parseDouble(point);
        return result;
    }

    @Override
    @Transactional
    public void deleteTaskAndImage(Long id) {
        String imageName = getTaskById(id)
                .orElseThrow(ResourceNotFoundException::new)
                .getImageFileName();
        deleteTaskById(id);
        if (imageName != null) imageService.deleteImage(imageName);
    }

    @Override
    @Transactional
    public void copyTask(TaskListDto taskTitleDto) {
        TaskDto taskDto = getTaskById(taskTitleDto.getId())
                .map(taskMapper::toDto)
                .orElseThrow(ResourceNotFoundException::new);
        Task task = taskMapper.toEntity(taskDto);
        task.setId(null);
        task.setTitle(taskTitleDto.getTitle());
        task.setType(3);
        task.setStatus(1);
        task.setMaxPoints(parseAndEvalPoints(task.getGradePoints()));
        String imageName = task.getImageFileName();
        if (imageName != null) {
            String extension = imageService.getExtensionFromImageName(imageName);
            task.setImageFileName(FileNameGenerator.generateFileName(extension));
            saveOrUpdate(task, imageService.getImageWithName(imageName));
        } else {
            saveOrUpdate(task, false);
        }
    }

    @Override
    @Transactional
    public void unarchiveTask(Long id) {
        Task task = getTaskById(id).orElseThrow(ResourceNotFoundException::new);
        task.setStatus(1);
        saveOrUpdate(task, false);
    }
}
