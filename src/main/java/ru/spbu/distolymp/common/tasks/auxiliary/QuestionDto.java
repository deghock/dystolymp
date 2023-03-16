package ru.spbu.distolymp.common.tasks.auxiliary;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.validation.admin.tasks.validator.QuestionsValidator;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
public class QuestionDto {
    private int number;

    private int oldNumber;

    private Long testId;

    private QuestionType type;

    @NotNull
    @Size(max = 65535)
    private String text;

    private String folderName;

    private String imageName;

    private MultipartFile image;

    private boolean deleteImage;

    private String difficulty;

    private String[] answers;

    private String[] trueAnswers;

    @AssertTrue(message = "{questions.pattern}")
    private boolean isQuestionValid() {
        return QuestionsValidator.isValid(type, answers, trueAnswers);
    }
}
