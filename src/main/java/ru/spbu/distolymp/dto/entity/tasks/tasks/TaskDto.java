package ru.spbu.distolymp.dto.entity.tasks.tasks;

import lombok.Data;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TaskDto {
    // TODO: Add conditions
    private Long id;
    private String prefix;
    private String title;
    private String problemText;
    private String imageFileName;
    private Integer width;
    private Integer height;
    private String comment;
    private String variables;
    private Integer answerNote;
    private String correctAnswer;
    private String gradePoints;
    private Double minusPoints;
    private Double minPoints;
}
