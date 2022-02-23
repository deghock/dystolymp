package ru.spbu.distolymp.dto.admin.models;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * @author Vladislav Konovalov
 */
@Data
public class ModelViewDto {
    private Long id;
    private String imageFileName;
    private Integer width;
    private Integer height;
    private String barsicFileName;
    private String parsedProblemText;
    private String currentServerDateTime;
    private String variableNameValue;
    private Map<String, String> variableNameComment;
    private List<String> answerNameList;
}
