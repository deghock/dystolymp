package ru.spbu.distolymp.mapper.admin.tasks.tasks.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spbu.distolymp.common.tasks.TaskEvaluator;
import ru.spbu.distolymp.common.tasks.TaskParser;
import ru.spbu.distolymp.dto.admin.tasks.tasks.TaskPreviewDto;
import ru.spbu.distolymp.entity.tasks.Task;
import ru.spbu.distolymp.mapper.admin.tasks.tasks.api.TaskPreviewMapper;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import static ru.spbu.distolymp.common.tasks.TaskTextParser.parse;

/**
 * @author Vladislav Konovalov
 */
@Component
@RequiredArgsConstructor
public class TaskPreviewMapperImpl implements TaskPreviewMapper {
    @Override
    public TaskPreviewDto toDto(Task task) {
        if (task == null) return null;
        TaskPreviewDto taskDto = new TaskPreviewDto();
        TaskEvaluator evaluator = new TaskEvaluator(task.getVariables(), task.getCorrectAnswer());
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");

        taskDto.setId(task.getId());
        taskDto.setImageFileName(task.getImageFileName());
        taskDto.setWidth(task.getWidth());
        taskDto.setHeight(task.getHeight());
        taskDto.setAnswerNote(task.getAnswerNote());
        taskDto.setParsedProblemText(parse(task.getProblemText(), evaluator.getVariableMap()));
        taskDto.setCurrentServerDateTime(dateTime.toLocalDateTime().format(formatter));
        taskDto.setAnswerNameValue(evaluator.getAnswerMap());
        taskDto.setVariableNameComment(evaluator.getCommentForVariableMap());
        taskDto.setAnswerNameList(TaskParser.getVarNames(task.getCorrectAnswer()));

        return taskDto;
    }
}
