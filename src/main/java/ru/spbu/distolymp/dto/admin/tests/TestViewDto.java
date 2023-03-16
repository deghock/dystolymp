package ru.spbu.distolymp.dto.admin.tests;

import lombok.Data;
import ru.spbu.distolymp.common.tasks.auxiliary.QuestionDto;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TestViewDto {
    private Long id;
    private boolean questionSkip;
    private String currentServerDateTime;
    private String title;
    private Integer questionNumber;
    private String testFolder;
    private List<QuestionDto> questionList;
}
