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

    public String getVariableString(boolean html) {
        StringBuilder variableNameValueString = new StringBuilder();
        Iterator<Entry<String, Object>> iterator = getVariableMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, Object> variable = iterator.next();
            String name = variable.getKey();
            Object value = variable.getValue();
            if (!(value instanceof String))
                variableNameValueString.append(name).append(" = ").append(value.toString()).append(";");
            if (iterator.hasNext())
                variableNameValueString.append(html ? "<br>" : "\n");
        }
        return variableNameValueString.toString();
    }

    public Map<String, Number> getAnswerMap() {
        Map<String, Number> answerNameValueMap = new HashMap<>();
        for (Entry<String, Entry<Number, Number>> ans : answers.entrySet()) {
            String name = ans.getKey();
            Number value = ans.getValue().getKey();
            answerNameValueMap.put(name, value);
        }
        return answerNameValueMap;
    }

    public String getAnswerString(boolean html) {
        StringBuilder answerNameValueString = new StringBuilder();
        Iterator<Entry<String, Number>> iterator = getAnswerMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, Number> answer = iterator.next();
            String name = answer.getKey();
            String value = answer.getValue().toString();
            answerNameValueString.append(name).append(" = ").append(value).append(";");
            if (iterator.hasNext())
                answerNameValueString.append(html ? "<br>" : "\n");
        }
        return answerNameValueString.toString();
    }

    public Map<String, Entry<Number, Number>> getAnswerWithErrorMap() {
        return answers;
    }

    public Map<String, String> getCommentForVariableMap() {
        Map<String, String> commentNameValueMap = new HashMap<>();
        for (Entry<String, Object> variable : variables.entrySet()) {
            String commentName = variable.getKey();
            String value = variable.getValue().toString();
            if (commentName.matches("^comment_\\w+$") ||
                    commentName.matches("^commentAfter_\\w+$")) {
                commentNameValueMap.put(commentName, value);
            }
        }
        return commentNameValueMap;
    }

    public Map<String, Boolean> checkTaskCorrectness(Map<String, Number> userAnswerMap) {
        Map<String, Boolean> correctnessMap = new HashMap<>();
        for (Entry<String, Number> userAnswer : userAnswerMap.entrySet()) {
            String name = userAnswer.getKey();
            if (userAnswer.getValue() == null) {
                correctnessMap.put(name, false);
                continue;
            }
            double userValue = userAnswer.getValue().doubleValue();
            if (answers.containsKey(name)) {
                double correctValue = answers.get(name).getKey().doubleValue();
                double error = answers.get(name).getValue().doubleValue();
                boolean correct = (userValue >= correctValue - error) && (userValue <= correctValue + error);
                correctnessMap.put(name, correct);
            } else {
                correctnessMap.put(name, false);
            }
        }
        return correctnessMap;
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
                variables = new HashMap<>();
                return;
            }
            String varName = line.split("=")[0].trim();
            if (engine.get(varName) != null)
                variables.put(varName, engine.get(varName));
        }
        if (lines.size() != variables.size())
            variables = new HashMap<>();
    }

    private void evalAnswers(String answerInput) {
        if (answerInput == null || answerInput.trim().equals("")) return;
        List<String> lines = parseTaskConditionToLines(answerInput);
        for (String line : lines) {
            line = line.trim();
            line = parseMathExpr(line);
            String errorStr = null;
            int plusMinusIndex = line.lastIndexOf("+-");
            if (plusMinusIndex != -1) {
                errorStr = line.substring(plusMinusIndex + 2);
                line = line.substring(0, plusMinusIndex);
            }
            try {
                engine.eval(line);
            } catch (ScriptException e) {
                answers = new HashMap<>();
                return;
            }
            String ansName = line.split("=")[0].trim();
            Number error;
            Object variableValue = engine.get(ansName);
            if (!(variableValue instanceof Number)) {
                answers = new HashMap<>();
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
                            answers = new HashMap<>();
                            return;
                        }
                    } else {
                        errorStr = errorStr.substring(0, percentIndex);
                        error = (Number) engine.eval(errorStr);
                        if (error.doubleValue() < 0.0) {
                            answers = new HashMap<>();
                            return;
                        }
                        error = Math.abs(((Number) variableValue).doubleValue() * error.doubleValue() / 100);
                    }
                } catch (ScriptException | ClassCastException e) {
                    answers = new HashMap<>();
                    return;
                }
            }
            if (engine.get(ansName) != null)
                answers.put(ansName, new AbstractMap.SimpleEntry<>((Number) variableValue, error));
        }
        if (lines.size() != answers.size())
            answers = new HashMap<>();
    }
}
