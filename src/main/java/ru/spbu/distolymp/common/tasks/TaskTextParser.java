package ru.spbu.distolymp.common.tasks;

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
            while (parsedProblemText.indexOf(openBracket) != -1 &&
                    parsedProblemText.indexOf(closeBracket) != -1) {
                int start = parsedProblemText.indexOf(openBracket);
                int end = parsedProblemText.indexOf(closeBracket);
                String varName = parsedProblemText.substring(start + 1, end);
                if (variables.containsKey(varName)) {
                    parsedProblemText.replace(start, end + 1, variables.get(varName).toString());
                } else {
                    parsedProblemText.replace(start, end + 1, varName);
                }
            }
        }
        return parsedProblemText.toString();
    }
}
