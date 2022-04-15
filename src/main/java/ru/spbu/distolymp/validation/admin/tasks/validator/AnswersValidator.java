package ru.spbu.distolymp.validation.admin.tasks.validator;

import ru.spbu.distolymp.common.tasks.parser.TaskEvaluator;
import static ru.spbu.distolymp.common.tasks.parser.PointParser.parsePoints;
import static ru.spbu.distolymp.common.tasks.parser.TaskParser.parseTaskConditionToLines;

/**
 * @author Vladislav Konovalov
 */
public class AnswersValidator {
    private AnswersValidator() {}

    public static boolean isValid(String variables, String answer) {
        TaskEvaluator evaluator = new TaskEvaluator(variables, answer);
        boolean variablesEmpty = (variables == null) || (variables.trim().equals(""));
        if (!variablesEmpty && evaluator.getVariableMap().isEmpty()) return true;
        return !evaluator.getAnswerWithErrorMap().isEmpty();
    }

    public static boolean isAnswerNumberValid(String gradePoints, String answer) {
        return parseTaskConditionToLines(answer).size() == parsePoints(gradePoints).size();
    }
}
