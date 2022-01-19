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
import ru.spbu.distolymp.mapper.admin.tasks.tasks.TaskListMapper;
import ru.spbu.distolymp.mapper.entity.tasks.TaskMapper;
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

    protected TaskServiceImpl(TaskRepository taskRepository,
                           TaskListMapper taskListMapper,
                           TaskMapper taskMapper) {
        super(taskRepository, taskListMapper, taskMapper);
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

    @Override
    @Transactional(readOnly = true)
    public void fillShowEditPageModelMap(ModelMap modelMap) {
        TaskDto taskDto = new TaskDto();
        taskDto.setAnswerNote(2);
        modelMap.put("task", taskDto);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowEditPageModelMap(Long id, ModelMap modelMap) {
        TaskDto taskDto = getTaskById(id);
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
    @Transactional
    public void deleteTaskAndImage(Long id) {
        String imageName = getTaskById(id).getImageFileName();
        if (imageName != null)
            imageService.deleteImage(imageName);
        deleteTaskById(id);
    }
}
