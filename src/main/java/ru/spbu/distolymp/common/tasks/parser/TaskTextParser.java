package ru.spbu.distolymp.common.tasks.parser;

import java.util.Map;

/**
 * @author Vladislav Konovalov
 */
public class TaskTextParser {
    private TaskTextParser() {}

    public static String parse(String problemText, Map<String, Object> variables) {
        if (problemText == null) return null;
        if (variables == null) return problemText;
        StringBuilder parsedProblemText = new StringBuilder(problemText);
        String[] brackets = new String[] {"{}", "[]"};
        for (String bracket : brackets) {
            String openBracket = bracket.substring(0, 1);
            String closeBracket = bracket.substring(1, 2);
            int oldStart = 0;
            int oldEnd = 0;
            while (parsedProblemText.indexOf(openBracket, oldStart) != -1 &&
                    parsedProblemText.indexOf(closeBracket, oldEnd) != -1) {
                int start = parsedProblemText.indexOf(openBracket, oldStart);
                int end = parsedProblemText.indexOf(closeBracket, oldEnd);
                oldStart = start + 1;
                oldEnd = end + 1;
                String varName = parsedProblemText.substring(start + 1, end);
                if (variables.containsKey(varName)) {
                    parsedProblemText.replace(start, end + 1, variables.get(varName).toString());
                }
            }
        }
        return parsedProblemText.toString();
    }
}
