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
        while (parsedProblemText.indexOf("{") != -1 && parsedProblemText.indexOf("}") != -1) {
            int start = parsedProblemText.indexOf("{");
            int end = parsedProblemText.indexOf("}");
            String varName = parsedProblemText.substring(start + 1, end);
            if (variables.containsKey(varName)) {
                parsedProblemText.replace(start, end + 1, variables.get(varName).toString());
            } else {
                parsedProblemText.replace(start, end + 1, varName);
            }
        }
        return parsedProblemText.toString();
    }
}
