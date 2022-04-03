package ru.spbu.distolymp.validation.admin.tasks.validator;

import ru.spbu.distolymp.common.tasks.auxiliary.QuestionType;

/**
 * @author Vladislav Konovalov
 */
public class QuestionsValidator {
    private QuestionsValidator() {}

    public static boolean isValid(QuestionType type, String[] answers, String[] trueAnswers) {
        boolean result = false;
        boolean flag = false;
        switch (type) {
            case S:
                if (trueAnswers[0] == null) break;
                for (int i = 0; i < 5; i++) {
                    if (answers[i] == null && !trueAnswers[0].equals(String.valueOf(i + 1))) continue;
                    if (answers[i] != null) result = true;
                    if (trueAnswers[0].equals(String.valueOf(i + 1)) && answers[i] == null) return false;
                }
                break;
            case C:
                for (int i = 0; i < 5; i++) {
                    if (trueAnswers[i] != null) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) break;
                for (int i = 0; i < 5; i++) {
                    if (answers[i] == null && trueAnswers[i] == null) continue;
                    if (answers[i] != null) result = true;
                    if (trueAnswers[i] != null && answers[i] == null) return false;
                }
                break;
            case L:
                for (int i = 0; i < 5; i++) {
                    if (answers[i] == null && trueAnswers[i] == null) continue;
                    if (answers[i] != null && trueAnswers[i] != null)
                        result = true;
                    else
                        return false;
                }
                break;
            case I:
                if (answers[0] != null) result = true;
                break;
            case F:
                if (answers[0] != null && answers[2] != null) result = true;
                break;
            default:
                break;
        }
        return result;
    }
}
