package ru.spbu.distolymp.common.tasks.parser;

import ru.spbu.distolymp.common.tasks.auxiliary.QuestionDto;
import ru.spbu.distolymp.common.tasks.auxiliary.QuestionType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public class TestParser {
    private TestParser() {}

    public static String getName(String paramFileContent) {
        String expr = "$test_name='";
        if (!paramFileContent.contains(expr)) return "";
        int start = paramFileContent.indexOf(expr) + expr.length();
        int end = paramFileContent.indexOf("';", start);
        return paramFileContent.substring(start, end);
    }

    public static boolean getRandomOrder(String paramFileContent) {
        String expr = "$question_order=";
        if (!paramFileContent.contains(expr)) return false;
        int start = paramFileContent.indexOf(expr) + expr.length();
        int end = start + 1;
        String resultStr = paramFileContent.substring(start, end);
        return resultStr.equals("1");
    }

    public static boolean getQuestionSkip(String paramFileContent) {
        String expr = "$question_skip=";
        if (!paramFileContent.contains(expr)) return false;
        int start = paramFileContent.indexOf(expr);
        if (start == -1) return true;
        start += expr.length();
        int end = paramFileContent.indexOf(";", start);
        String resultStr = paramFileContent.substring(start, end);
        return resultStr.equals("true");
    }

    public static int[] getQuestionsNumber(String paramFileContent) {
        int[] result = new int[3];
        for (int i = 0; i < result.length; i++) {
            String expr = "$question_count_" + ((i + 1) * 2) + "=";
            if (!paramFileContent.contains(expr)) return new int[] {0, 0, 0};
            int start = paramFileContent.indexOf(expr) + expr.length();
            int end = paramFileContent.indexOf(";", start);
            String resultStr = paramFileContent.substring(start, end);
            try {
                result[i] = Integer.parseInt(resultStr);
            } catch (NumberFormatException e) {
                result[i] = 0;
            }
        }
        return result;
    }

    public static List<QuestionDto> getQuestions(String paramFileContent) {
        List<QuestionDto> result = new ArrayList<>();
        List<String> questionsStr = getQuestionsString(paramFileContent);
        int number = 1;

        for (String questionStr : questionsStr) {
            QuestionDto questionDto = new QuestionDto();
            QuestionType type = getType(questionStr);
            questionDto.setType(type);
            questionDto.setText(getText(questionStr));
            questionDto.setImageName(getImageName(questionStr));
            questionDto.setDifficulty(getDifficulty(questionStr));
            questionDto.setAnswers(getAnswers(questionStr, type));
            questionDto.setTrueAnswers(getTrueAnswers(questionStr, type));
            questionDto.setNumber(number);
            result.add(questionDto);
            number++;
        }

        return result;
    }

    public static int[] getAllQuestionsNumber(List<QuestionDto> questionList) {
        int[] result = new int[] {0, 0, 0};

        for(QuestionDto question : questionList) {
            switch (question.getDifficulty()) {
                case "Лёгкий":
                    result[0]++;
                    break;
                case "Средний":
                    result[1]++;
                    break;
                case "Сложный":
                    result[2]++;
                    break;
                default:
                    break;
            }
        }

        return result;
    }

    private static List<String> getQuestionsString(String paramFileContent) {
        List<String> result = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();
        int currentIndex = 0;
        String expr = "=>array(\r\n         0=>'";
        if (!paramFileContent.contains(expr)) return new ArrayList<>();

        while (paramFileContent.indexOf(expr, currentIndex) != -1) {
            int index = paramFileContent.indexOf(expr, currentIndex);
            indexes.add(index);
            currentIndex = index + 1;
        }
        indexes.add(paramFileContent.lastIndexOf("?>"));

        for (int i = 1; i < indexes.size(); i++) {
            int start = indexes.get(i - 1);
            int end = indexes.get(i);
            result.add(paramFileContent.substring(start, end));
        }

        return result;
    }

    private static String getAnswersString(String questionContent) {
        String expr1 = "'answer'=>array(\r\n" +
                "                         ";
        if (!questionContent.contains(expr1)) return "";
        String expr2 = "),\r\n" +
                "         '";
        int start = questionContent.indexOf(expr1);
        int end = questionContent.indexOf(expr2, start);
        return questionContent.substring(start + expr1.length(), end);
    }

    private static String getTrueAnswersString(String questionContent) {
        String expr1 = "'true_answer'=>array(\r\n" +
                "                              ";
        if (!questionContent.contains(expr1)) return "";
        String expr2 = ")\r\n" +
                "         )";
        int start = questionContent.indexOf(expr1);
        int end = questionContent.indexOf(expr2, start);
        return questionContent.substring(start + expr1.length(), end);
    }

    private static QuestionType getType(String questionContent) {
        String expr = "0=>'";
        if (!questionContent.contains(expr)) return QuestionType.U;
        int start = questionContent.indexOf(expr) + expr.length();
        int end = start + 1;
        String type = questionContent.substring(start, end);
        switch (type) {
            case "S":
                return QuestionType.S;
            case "C":
                return QuestionType.C;
            case "L":
                return QuestionType.L;
            case "I":
                return QuestionType.I;
            case "F":
                return QuestionType.F;
            default:
                return QuestionType.U;
        }
    }

    private static String getText(String questionContent) {
        String expr = "1=>'";
        if (!questionContent.contains(expr)) return "";
        int start = questionContent.indexOf(expr) + expr.length();
        int end = questionContent.indexOf("',", start);
        return questionContent.substring(start, end);
    }

    private static String getImageName(String questionContent) {
        String expr = "2=>'";
        if (!questionContent.contains(expr)) return "";
        int start = questionContent.indexOf(expr) + expr.length();
        int end = questionContent.indexOf("',", start);
        return questionContent.substring(start, end);
    }

    private static String getDifficulty(String questionContent) {
        String expr1 = "5=>";
        if (!questionContent.contains(expr1)) return "Неизвестно";
        String expr2 = ",\r\n" +
                "         '";
        int start = questionContent.indexOf(expr1) + expr1.length();
        int end = questionContent.indexOf(expr2, start);
        String diff = questionContent.substring(start, end);
        switch (diff) {
            case "2":
                return "Лёгкий";
            case "4":
                return "Средний";
            case "6":
                return "Сложный";
            default:
                return "Неизвестно";
        }
    }

    private static String[] getAnswers(String questionContent, QuestionType type) {
        String[] result = new String[5];
        String expr1;
        String expr2 = "'";
        if (type == QuestionType.L)
            expr1 = "=>array('";
        else
            expr1 = "=>'";
        if (!questionContent.contains(expr1)) return new String[] {"", "", "", "", ""};
        String answersStr = getAnswersString(questionContent);
        int end = 0;
        for (int i = 0; i < result.length; i++) {
            int start = answersStr.indexOf(expr1, end);
            if (start == -1) {
                result[i] = "";
            } else {
                start = start + expr1.length();
                end = answersStr.indexOf(expr2, start);
                result[i] = answersStr.substring(start, end);
            }
        }
        return result;
    }

    private static String[] getTrueAnswers(String questionContent, QuestionType type) {
        String[] result = new String[5];
        String trueAnswersStr;
        String expr1;
        String expr2;
        if (type == QuestionType.L) {
            trueAnswersStr = getAnswersString(questionContent);
            expr1 = "','";
            expr2 = "')";
        } else {
            trueAnswersStr = getTrueAnswersString(questionContent);
            expr1 = "=>";
            expr2 = null;
        }
        if (!questionContent.contains(expr1)) return new String[] {"", "", "", "", ""};
        int end = 0;
        for (int i = 0; i < result.length; i++) {
            int start = trueAnswersStr.indexOf(expr1, end);
            if (start == -1) {
                result[i] = "";
                continue;
            } else {
                start += expr1.length();
            }
            end = (type == QuestionType.L) ? trueAnswersStr.indexOf(expr2, start) : start + 1;
            result[i] = trueAnswersStr.substring(start, end);
        }
        return result;
    }
}
