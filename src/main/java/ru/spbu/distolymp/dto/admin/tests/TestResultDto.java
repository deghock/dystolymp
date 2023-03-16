package ru.spbu.distolymp.dto.admin.tests;

import lombok.Data;
import ru.spbu.distolymp.common.tasks.auxiliary.QuestionDto;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Data
public class TestResultDto {
    private int interrupted;
    private boolean showResult;
    private String startDateTime;
    private Integer[] questionNumber;
    private String durationTime;
    private String points;
    private List<QuestionDto> questionList;
    private boolean[] correctness;
}
