package ru.spbu.distolymp.dto.entity.tasks;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.validation.admin.tasks.annotation.Points;
import ru.spbu.distolymp.validation.admin.tasks.annotation.Variables;
import ru.spbu.distolymp.validation.admin.tasks.validator.AnswersValidator;
import ru.spbu.distolymp.validation.files.annotation.BarsicFileMimeType;
import ru.spbu.distolymp.validation.files.annotation.ImageMimeType;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
public class ModelDto {
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

    @NotNull(message = "{tasks.gradePoints.required}")
    @Size(max = 255)
    @Points
    private String gradePoints;

    @Size(max = 65535)
    @Variables
    private String variables;

    @NotNull(message = "{tasks.correctAnswer.required}")
    @Size(max = 65535)
    private String correctAnswer;

    @NotNull
    @Range(max = 65535)
    private Double minusPoints;

    @NotNull
    @Range(max = 65535)
    private Double minPoints;

    private String barsicFileName;

    @NotNull
    @BarsicFileMimeType
    private MultipartFile barsicFile;

    @NotNull
    @Size(max = 65535)
    private String problemForm;

    @AssertTrue(message = "{answers.pattern}")
    private boolean isAnswerValid() {
        return AnswersValidator.isValid(variables, correctAnswer);
    }

    @AssertTrue(message = "{tasks.correctAnswer.number}")
    private boolean isAnswerNumberValid() {
        return AnswersValidator.isAnswerNumberValid(gradePoints, correctAnswer);
    }
}
