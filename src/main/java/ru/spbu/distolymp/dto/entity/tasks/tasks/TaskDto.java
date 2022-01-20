package ru.spbu.distolymp.dto.entity.tasks.tasks;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TaskDto {
    // TODO: Add conditions
    private Long id;
    private Integer status;
    private String prefix;
    private String title;
    private String problemText;
    private String imageFileName;
    private MultipartFile image;
    private boolean deleteImage;
    private Integer width;
    private Integer height;
    private String variables;
    private Integer answerNote;
    private String correctAnswer;
    private String gradePoints;
    private Double minusPoints;
    private Double minPoints;
}
