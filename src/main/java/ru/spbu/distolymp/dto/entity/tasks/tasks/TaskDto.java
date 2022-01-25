package ru.spbu.distolymp.dto.entity.tasks.tasks;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.validation.files.annotation.ImageMimeType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TaskDto {
    private Long id;

    private Integer status;

    @Size(max = 255)
    private String prefix;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    @Size(max = 65535)
    private String problemText;

    private String imageFileName;

    // TODO: size validation (?)
    @ImageMimeType
    private MultipartFile image;

    private boolean deleteImage;

    @NotNull
    @Range(max = 65535)
    private Integer width;

    @NotNull
    @Range(max = 65535)
    private Integer height;

    // TODO: custom validation by pattern
    @Size(max = 65535)
    private String variables;

    private Integer answerNote;

    // TODO: custom validation by pattern
    @Size(max = 65535)
    private String correctAnswer;

    // TODO: custom validation by pattern
    @NotNull
    @Size(max = 255)
    private String gradePoints;

    @NotNull
    @Range(max = 65535)
    private Double minusPoints;

    @NotNull
    @Range(max = 65535)
    private Double minPoints;

    //TODO: cross field validation (?)
}
