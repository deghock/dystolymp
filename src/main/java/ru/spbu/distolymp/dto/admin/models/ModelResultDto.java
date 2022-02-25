package ru.spbu.distolymp.dto.admin.models;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * @author Vladislav Konovalov
 */
@Data
public class ModelResultDto {
    private List<String> answerNameList;
    private Map<String, Number> userAnswerMap;
    private Map<String, Boolean> correctness;
    private List<String> points;
    private Double maxPoint;
    private Double userPoints;
}
