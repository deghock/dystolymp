package ru.spbu.distolymp.dto.entity.tasks.tasks;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.validation.admin.tasks.annotation.Points;
import ru.spbu.distolymp.validation.admin.tasks.annotation.Variables;
import ru.spbu.distolymp.validation.admin.tasks.validator.AnswersValidator;
import ru.spbu.distolymp.validation.files.annotation.ImageMimeType;
import javax.validation.constraints.AssertTrue;
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

    @ImageMimeType
    private MultipartFile image;

    private boolean deleteImage;

    @NotNull
    @Range(max = 65535)
    private Integer width;

    @NotNull
    @Range(max = 65535)
    private Integer height;

    @Size(max = 65535)
    @Variables
    private String variables;

    private Integer answerNote;

    @NotNull(message = "{task.correctAnswer.required}")
    @Size(max = 65535)
    private String correctAnswer;

    @NotNull(message = "{task.gradePoints.required}")
    @Size(max = 255)
    @Points
    private String gradePoints;

    @NotNull
    @Range(max = 65535)
    private Double minusPoints;

    @NotNull
    @Range(max = 65535)
    private Double minPoints;

    @AssertTrue(message = "{answers.pattern}")
    private boolean isAnswerValid() {
        return AnswersValidator.isValid(variables, correctAnswer);
    }

    @AssertTrue(message = "{task.correctAnswer.number}")
    private boolean isAnswerNumberValid() {
        return AnswersValidator.isAnswerNumberValid(gradePoints, correctAnswer);
    }
}
