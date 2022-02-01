package ru.spbu.distolymp.validation.admin.tasks.validator;

import ru.spbu.distolymp.common.tasks.TaskEvaluator;

/**
 * @author Vladislav Konovalov
 */
public class AnswersValidator {
    private AnswersValidator() {}

    public static boolean isValid(String variables, String answer) {
        TaskEvaluator evaluator = new TaskEvaluator();
        if (evaluator.evalAndGetVariables(variables).isEmpty()) return true;
        return !evaluator.evalAndGetAnswers(answer).isEmpty();
    }

    public static boolean isAnswerNumberValid(String gradePoints, String answer) {
        TaskEvaluator evaluator = new TaskEvaluator();
        PointsValidator validator = new PointsValidator();
        return evaluator.countLines(answer) == validator.countPoints(gradePoints);
    }
}
