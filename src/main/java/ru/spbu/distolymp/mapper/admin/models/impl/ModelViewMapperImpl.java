package ru.spbu.distolymp.mapper.admin.models.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spbu.distolymp.common.tasks.TaskEvaluator;
import ru.spbu.distolymp.common.tasks.TaskParser;
import ru.spbu.distolymp.common.tasks.TaskTextParser;
import ru.spbu.distolymp.dto.admin.models.ModelViewDto;
import ru.spbu.distolymp.entity.tasks.Model;
import ru.spbu.distolymp.mapper.admin.models.api.ModelViewMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Vladislav Konovalov
 */
@Component
@RequiredArgsConstructor
public class ModelViewMapperImpl implements ModelViewMapper {
    @Override
    public ModelViewDto toDto(Model model) {
        return toDto(model, model.getVariables());
    }

    @Override
    public ModelViewDto toDto(Model model, String params) {
        if (model == null) return null;
        ModelViewDto modelDto = new ModelViewDto();
        TaskEvaluator evaluator = new TaskEvaluator(params, model.getCorrectAnswer());
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");

        modelDto.setId(model.getId());
        modelDto.setImageFileName(model.getImageFileName());
        modelDto.setWidth(model.getWidth());
        modelDto.setHeight(model.getHeight());
        modelDto.setBarsicFileName(model.getBarsicFileName());
        modelDto.setParsedProblemText(TaskTextParser.parse(model.getProblemText(), evaluator.getVariableMap()));
        modelDto.setCurrentServerDateTime(dateTime.format(formatter));
        modelDto.setVariableNameValue(evaluator.getVariableString(false));
        modelDto.setVariableNameComment(evaluator.getCommentForVariableMap());
        modelDto.setAnswerNameList(TaskParser.getVarNames(model.getCorrectAnswer()));

        return modelDto;
    }
}
