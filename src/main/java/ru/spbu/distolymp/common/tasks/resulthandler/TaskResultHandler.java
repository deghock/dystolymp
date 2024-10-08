package ru.spbu.distolymp.common.tasks.resulthandler;

import ru.spbu.distolymp.common.tasks.parser.TaskEvaluator;
import ru.spbu.distolymp.common.tasks.parser.TaskParser;
import ru.spbu.distolymp.dto.admin.tasks.TaskResultDto;
import ru.spbu.distolymp.dto.admin.tasks.TaskViewDto;
import java.util.*;

/**
 * @author Vladislav Konovalov
 */
public class TaskResultHandler {
    private TaskResultHandler() {}

    public static TaskResultDto toResultDto(TaskViewDto taskDto,
                                            String[] userAnswers,
                                            String answersString,
                                            List<String> points,
                                            double maxPoint) {
        if (taskDto == null) return null;

        TaskResultDto resultDto = new TaskResultDto();
        TaskEvaluator evaluator = new TaskEvaluator(taskDto.getVariableNameValue(), answersString);

        resultDto.setAnswerNote(taskDto.getAnswerNote());

        List<String> param = new ArrayList<>();
        if (taskDto.getVariableNameValue() != null && !taskDto.getVariableNameValue().trim().equals("")) {
            param.add("Параметры:");
            param.addAll(TaskParser.parseTaskConditionToLines(taskDto.getVariableNameValue()));
        }
        if (!answersString.isEmpty() && !answersString.equals("answer=0")) {
            if (taskDto.getVariableNameValue() != null && !taskDto.getVariableNameValue().trim().equals(""))
                param.add("<br>");
            param.add("Ответы:");
            param.addAll(TaskParser.parseTaskConditionToLines(answersString));
        }
        resultDto.setParam(param);

        List<String> answerNameList = taskDto.getAnswerNameList();
        resultDto.setAnswerNameList(answerNameList);

        Map<String, String> userAnswerMap = new HashMap<>();
        if (userAnswers != null) {
            for (int i = 0; i < answerNameList.size(); i++) {
                userAnswerMap.put(answerNameList.get(i), userAnswers[i]);
            }
        }
        resultDto.setUserAnswerMap(userAnswerMap);

        resultDto.setAnswerWithErrorMap(evaluator.getAnswerWithErrorMap());

        resultDto.setCorrectness(evaluator.checkTaskCorrectness(userAnswerMap));

        int correctAnswersNumber = answerNameList.size();
        for (int i = 0; i < answerNameList.size(); i++) {
            String answerName = answerNameList.get(i);
            Boolean correct = resultDto.getCorrectness().get(answerName);
            if (correct != null && !correct) {
                points.set(i, "0");
                correctAnswersNumber--;
            }
        }
        resultDto.setPoints(points);

        resultDto.setMaxPoint(maxPoint);

        double userPoints = 0.0;
        for (String point : points)
            userPoints += Double.parseDouble(point);
        resultDto.setUserPoints(userPoints);

        resultDto.setCorrectAnswersNumber(correctAnswersNumber);

        return resultDto;
    }
}
