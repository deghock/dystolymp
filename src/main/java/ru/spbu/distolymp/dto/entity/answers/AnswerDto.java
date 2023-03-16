package ru.spbu.distolymp.dto.entity.answers;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
public class AnswerDto {
    private String ip;

    private Integer answerNote;

    private String param;

    private String[] userAnswers;

    @Size(max = 65535)
    private String userNotes;

    private MultipartFile userFile;

    private Long problemId;

    private Long userId;

    private String taskStartDateTime;
}
