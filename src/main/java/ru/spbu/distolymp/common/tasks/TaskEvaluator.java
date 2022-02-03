package ru.spbu.distolymp.common.tasks;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;
import java.util.Map.Entry;
import static ru.spbu.distolymp.common.tasks.TaskParser.*;

/**
 * @author Vladislav Konovalov
 */
public class TaskEvaluator {
    private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("Nashorn");
    private Map<String, Object> variables = new HashMap<>();
    private Map<String, Entry<Number, Number>> answers = new HashMap<>();

    public TaskEvaluator(String variables, String answers) {
        evalVariables(variables);
        evalAnswers(answers);
    }

    public TaskEvaluator(String variables) {
        evalVariables(variables);
    }

    public Map<String, Object> getVariableMap() {
        return variables;
    }

    public Map<String, Number> getAnswerMap() {
        Map<String, Number> result = new HashMap<>();
        for (Entry<String, Entry<Number, Number>> ans : answers.entrySet()) {
            String name = ans.getKey();
            Number value = ans.getValue().getKey();
            result.put(name, value);
        }
        return result;
    }

    public Map<String, Entry<Number, Number>> getAnswerWithErrorMap() {
        return answers;
    }

    public Map<String, String> getCommentForVariableMap() {
        Map<String, String> result = new HashMap<>();
        for (Entry<String, Object> variable : variables.entrySet()) {
            String commentName = variable.getKey();
            String value = variable.getValue().toString();
            if (commentName.matches("^comment_\\w+$"))
                result.put(commentName, value);
            if (commentName.matches("^commentAfter_\\w+$"))
                result.put(commentName, value);
        }
        return result;
    }

    private void evalVariables(String variablesInput) {
        if (variablesInput == null || variablesInput.trim().equals("")) return;
        List<String> lines = parseTaskConditionToLines(variablesInput);
        for (String line : lines) {
            line = line.trim();
            line = parseMathExpr(line);
            try {
                line = parseList(line);
                line = parseInterval(line);
                engine.eval(line);
            } catch (ScriptException e) {
                variables = Collections.emptyMap();
                return;
            }
            String varName = line.split("=")[0].trim();
            variables.put(varName, engine.get(varName));
        }
    }

    private void evalAnswers(String answerInput) {
        if (answerInput == null || answerInput.trim().equals("")) return;
        List<String> lines = parseTaskConditionToLines(answerInput);
        for (String line : lines) {
            line = line.trim();
            line = parseMathExpr(line);
            line = line.replace(",", ".");
            String errorStr = null;
            int plusMinusIndex = line.lastIndexOf("+-");
            if (plusMinusIndex != -1) {
                errorStr = line.substring(plusMinusIndex + 2);
                line = line.substring(0, plusMinusIndex);
            }
            try {
                engine.eval(line);
            } catch (ScriptException e) {
                answers = Collections.emptyMap();
                return;
            }
            String ansName = line.split("=")[0].trim();
            Number error;
            Object variableValue = engine.get(ansName);
            if (!(variableValue instanceof Number)) {
                answers = Collections.emptyMap();
                return;
            }
            if (errorStr == null) {
                error = 0.05;
            } else {
                int percentIndex = errorStr.lastIndexOf("%");
                try {
                    if (percentIndex == -1) {
                        error = (Number) engine.eval(errorStr);
                        if (error.doubleValue() < 0.0) {
                            answers = Collections.emptyMap();
                            return;
                        }
                    } else {
                        errorStr = errorStr.substring(0, percentIndex);
                        error = (Number) engine.eval(errorStr);
                        if (error.doubleValue() < 0.0) {
                            answers = Collections.emptyMap();
                            return;
                        }
                        error = Math.abs(((Number) variableValue).doubleValue() * error.doubleValue() / 100);
                    }
                } catch (ScriptException | ClassCastException e) {
                    answers = Collections.emptyMap();
                    return;
                }
            }
            answers.put(ansName, new AbstractMap.SimpleEntry<>((Number) variableValue, error));
        }
    }
}
