package ru.spbu.distolymp.dto.admin.tasks.tasks;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TaskPreviewResultDto {
    private Integer answerNote;
    private List<String> param;
    private List<String> answerNameList;
    private Map<String, Number> userAnswerMap;
    private Map<String, Map.Entry<Number, Number>> answerWithErrorMap;
    private Map<String, Boolean> correctness;
    private List<String> points;
    private Double maxPoint;
    private Double userPoints;
    private Integer correctAnswersNumber;
}
