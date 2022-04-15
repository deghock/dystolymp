package ru.spbu.distolymp.common.tasks.resulthandler;

import ru.spbu.distolymp.common.tasks.parser.TaskEvaluator;
import ru.spbu.distolymp.dto.admin.models.ModelResultDto;
import ru.spbu.distolymp.dto.admin.models.ModelViewDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vladislav Konovalov
 */
public class ModelResultHandler {
    private ModelResultHandler() {}

    public static ModelResultDto toResultDto(ModelViewDto modelDto,
                                             String[] userAnswers,
                                             String answersString,
                                             List<String> points,
                                             double maxPoint) {
        if (modelDto == null) return null;

        ModelResultDto resultDto = new ModelResultDto();
        TaskEvaluator evaluator = new TaskEvaluator(modelDto.getVariableNameValue(), answersString);

        List<String> answerNameList = modelDto.getAnswerNameList();
        resultDto.setAnswerNameList(answerNameList);

        Map<String, String> userAnswerMap = new HashMap<>();
        if (userAnswers != null) {
            for (int i = 0; i < userAnswers.length; i++) {
                userAnswerMap.put(answerNameList.get(i), userAnswers[i]);
            }
        }
        resultDto.setUserAnswerMap(userAnswerMap);

        resultDto.setCorrectness(evaluator.checkTaskCorrectness(userAnswerMap));

        for (int i = 0; i < answerNameList.size(); i++) {
            String answerName = answerNameList.get(i);
            Boolean correct = resultDto.getCorrectness().get(answerName);
            if (correct != null && !correct)
                points.set(i, "0");
        }
        resultDto.setPoints(points);

        resultDto.setMaxPoint(maxPoint);

        double userPoints = 0.0;
        for (String point : points)
            userPoints += Double.parseDouble(point);
        resultDto.setUserPoints(userPoints);

        return resultDto;
    }
}
