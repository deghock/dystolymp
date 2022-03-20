package ru.spbu.distolymp.mapper.admin.tasks.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spbu.distolymp.common.tasks.TaskEvaluator;
import ru.spbu.distolymp.common.tasks.TaskParser;
import ru.spbu.distolymp.dto.admin.tasks.TaskViewDto;
import ru.spbu.distolymp.entity.tasks.Task;
import ru.spbu.distolymp.mapper.admin.tasks.api.TaskViewMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static ru.spbu.distolymp.common.tasks.TaskTextParser.parse;

/**
 * @author Vladislav Konovalov
 */
@Component
@RequiredArgsConstructor
public class TaskViewMapperImpl implements TaskViewMapper {
    @Override
    public TaskViewDto toDto(Task task) {
        return toDto(task, task.getVariables());
    }

    @Override
    public TaskViewDto toDto(Task task, String params) {
        if (task == null) return null;
        TaskViewDto taskDto = new TaskViewDto();
        TaskEvaluator evaluator = new TaskEvaluator(params, task.getCorrectAnswer());
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");

        taskDto.setId(task.getId());
        taskDto.setImageFileName(task.getImageFileName());
        taskDto.setWidth(task.getWidth());
        taskDto.setHeight(task.getHeight());
        taskDto.setAnswerNote(task.getAnswerNote());
        taskDto.setParsedProblemText(parse(task.getProblemText(), evaluator.getVariableMap()));
        taskDto.setCurrentServerDateTime(dateTime.format(formatter));
        taskDto.setAnswerNameList(TaskParser.getVarNames(task.getCorrectAnswer()));
        taskDto.setVariableNameValue(evaluator.getVariableString(false));
        evaluator = new TaskEvaluator(task.getVariables());
        taskDto.setVariableNameComment(evaluator.getCommentForVariableMap());

        return taskDto;
    }
}
