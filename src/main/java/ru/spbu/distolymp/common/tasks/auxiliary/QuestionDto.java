package ru.spbu.distolymp.common.tasks.auxiliary;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Vladislav Konovalov
 */
@Data
public class QuestionDto {
    private QuestionType type;
    private String text;
    private String imageName;
    private MultipartFile image;
    private boolean deleteImage;
    private String difficulty;
    private String[] answers;
    private String[] trueAnswers;
}
