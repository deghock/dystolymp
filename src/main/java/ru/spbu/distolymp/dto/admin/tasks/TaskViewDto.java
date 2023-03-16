package ru.spbu.distolymp.dto.admin.tasks;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TaskViewDto {
    private Long id;
    private String imageFileName;
    private Integer width;
    private Integer height;
    private Integer answerNote;
    private String parsedProblemText;
    private String currentServerDateTime;
    private Map<String, String> variableNameComment;
    private List<String> answerNameList;
    private String variableNameValue;
}
