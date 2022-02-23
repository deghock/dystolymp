package ru.spbu.distolymp.dto.admin.models;

import lombok.Data;

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
}
