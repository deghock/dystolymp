package ru.spbu.distolymp.common.tasks;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;

/**
 * It is used for parsing and calculating variables, as well as answers with errors for tasks.
 * It can also be used to validate data fields.
 * The Nashorn JavaScript Engine is used for the calculation.
 *
 * @author Vladislav Konovalov
 */
public class TaskEvaluator {
    private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("Nashorn");
    private static final HashMap<String, String> maths = new HashMap<>();

    static {
        maths.put("abs(", "Math.abs(");
        maths.put("sin(", "Math.sin(");
        maths.put("arcsin(", "Math.asin(");
        maths.put("cos(", "Math.cos(");
        maths.put("arccos(", "Math.acos(");
        maths.put("tg(", "Math.tan(");
        maths.put("arctg(", "Math.atan(");
        maths.put("floor(", "Math.floor(");
        maths.put("round(", "Math.round(");
        maths.put("ceil(", "Math.ceil(");
        maths.put("exp(", "Math.exp(");
        maths.put("ln(", "Math.log(");
        maths.put("log10(", "1.0/Math.LN10*Math.log(");
        maths.put("max(", "Math.max(");
        maths.put("min(", "Math.min(");
        maths.put("pow(", "Math.pow(");
        maths.put("sqrt(", "Math.sqrt(");
        maths.put("pi()", "Math.PI");
    }


    /**
     * This method checks whether the problem is solved correctly for all answer variables.
     *
     * @param taskCondition the condition of the task, where the String is the name of the variable
     *                      and the Object is the value of the variable
     * @param solutions     solutions to the task, where the String is the name of an answer
     *                      variable and the Double is the value of an answer variable
     * @param answerInput   formulas that can be used to find a solution to the task
     *                      ("correctAnswer" field in {@link ru.spbu.distolymp.entity.tasks.Task})
     * @return the map that contains the String as the answer variable name and
     *         the Boolean as the result of the solution for this answer variable
     */
    public Map<String, Boolean> areSolutionsRight(Map<String, Object> taskCondition,
                                                  Map<String, Double> solutions,
                                                  String answerInput) {
        Map<String, Boolean> results = new HashMap<>();
        StringBuilder conditionInput = new StringBuilder();
        for (Map.Entry<String, Object> condition : taskCondition.entrySet())
            conditionInput.append(condition.getKey())
                    .append(" = ")
                    .append(condition.getValue())
                    .append(";");
        try {
            engine.eval(conditionInput.toString());
        } catch (ScriptException e) {
            return Collections.emptyMap();
        }
        Map<String, Map.Entry<Number, Number>> answer = evalAndGetAnswers(answerInput);
        if (solutions.size() != answer.size()) return Collections.emptyMap();
        for (Map.Entry<String, Double> solution : solutions.entrySet()) {
            String answerName = solution.getKey();
            Map.Entry<Number, Number> valueWithError = answer.get(answerName);
            if (valueWithError == null) return Collections.emptyMap();
            double userAnswer = solution.getValue();
            double correctAnswer = valueWithError.getKey().doubleValue();
            double answerError = valueWithError.getValue().doubleValue();
            boolean resultForAnswer = (userAnswer >= correctAnswer - answerError) &&
                    (userAnswer <= correctAnswer + answerError);
            results.put(answerName, resultForAnswer);
        }
        return results;
    }

    /**
     * This method parses the string of variables and executes it on the JavaScript Engine.
     *
     * @param input the String of the variables
     * @return the map that contains the String as the variable name and
     *         the Object as the result of calculating a variable
     */
    public Map<String, Object> evalAndGetVariables(String input) {
        Map<String, Object> variables = new HashMap<>();
        if (input == null || input.trim().equals("")) return Collections.emptyMap();
        List<String> lines = parseInputToLines(input);
        for (String line : lines) {
            line = line.trim();
            line = parseMathExpr(line);
            try {
                line = parseList(line);
                line = parseInterval(line);
                engine.eval(line);
            } catch (ScriptException e) {
                return Collections.emptyMap();
            }
            String varName = line.split("=")[0].trim();
            variables.put(varName, engine.get(varName));
        }
        return variables;
    }

    /**
     * This method parses the string of answer variables and executes it on the JavaScript Engine.
     *
     * @param input the String of the answer variables
     * @return the map containing the first parameter (String) as the answer variable name,
     *         the second parameter (Number) as the answer variable value and
     *         the third parameter (Number) as the calculated error of the answer variable
     */
    public Map<String, Map.Entry<Number, Number>> evalAndGetAnswers(String input) {
        Map<String, Map.Entry<Number, Number>> answers = new HashMap<>();
        if (input == null || input.trim().equals("")) return Collections.emptyMap();
        List<String> lines = parseInputToLines(input);
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
                return Collections.emptyMap();
            }
            String ansName = line.split("=")[0].trim();
            Number error;
            Object variableValue = engine.get(ansName);
            if (!(variableValue instanceof Number)) return Collections.emptyMap();
            if (errorStr == null) {
                error = 0.05;
            } else {
                int percentIndex = errorStr.lastIndexOf("%");
                try {
                    if (percentIndex == -1) {
                        error = (Number) engine.eval(errorStr);
                        if (error.doubleValue() < 0.0) return Collections.emptyMap();
                    } else {
                        errorStr = errorStr.substring(0, percentIndex);
                        error = (Number) engine.eval(errorStr);
                        if (error.doubleValue() < 0.0) return Collections.emptyMap();
                        error = Math.abs(((Number) variableValue).doubleValue() * error.doubleValue() / 100);
                    }
                } catch (ScriptException | ClassCastException e) {
                    return Collections.emptyMap();
                }
            }
            answers.put(ansName, new AbstractMap.SimpleEntry<>((Number) variableValue, error));
        }
        return answers;
    }

    /**
     * This method counts the number of rows in the input.
     * A string is a set of characters in which there is an "=" and
     * the strings are separated by a semicolon.
     *
     * @param input any input data suitable for parsing by this method
     * @return the number of rows
     */
    public int countLines(String input) {
        if (input == null || input.trim().equals("")) return 0;
        return parseInputToLines(input).size();
    }

    private List<String> parseInputToLines(String input) {
        List<String> lines = new ArrayList<>();
        List<Integer> equalIndexes = new ArrayList<>();
        List<Integer> separatorIndexes = new ArrayList<>();
        separatorIndexes.add(-1);
        for (int i = input.indexOf("="); i > -1; i = input.indexOf("=", i + 1))
            equalIndexes.add(i);
        for (int i = 0; i < equalIndexes.size() - 1; i++) {
            String substr = input.substring(equalIndexes.get(i) + 1, equalIndexes.get(i + 1));
            int semicolonIndex = substr.lastIndexOf(";");
            separatorIndexes.add(equalIndexes.get(i) + semicolonIndex + 1);
        }
        separatorIndexes.add(input.length() - 1);
        for (int i = 0; i < separatorIndexes.size() - 1; i++) {
            String substr = input.substring(separatorIndexes.get(i) + 1, separatorIndexes.get(i + 1) + 1);
            lines.add(substr);
        }
        return lines;
    }

    private String parseList(String input) throws ScriptException {
        StringBuilder result = new StringBuilder(input);
        String listMsg = "Неверно задан список значений";
        while (result.indexOf("[") != -1 && result.indexOf("]") != -1) {
            int first = result.indexOf("[");
            int last = result.indexOf("]");
            if (last - first < 2)
                throw new ScriptException(listMsg);
            String substr = result.substring(first + 1, last);
            if (isListInvalid(substr))
                throw new ScriptException(listMsg);
            String[] valuesArray = substr.split(",");
            for (String value : valuesArray)
                if (value.trim().equals("")) throw new ScriptException(listMsg);
            List<String> values = Arrays.asList(valuesArray);
            Collections.shuffle(values);
            result.replace(first, last + 1, values.get(0));
        }
        return result.toString();
    }

    private boolean isListInvalid(String parsedList) {
        String str = parsedList.replaceAll("\\s", "");
        char[] chars = str.toCharArray();
        return chars[0] == ',' || chars[chars.length - 1] == ',';
    }

    private String parseInterval(String input) throws ScriptException {
        StringBuilder result = new StringBuilder(input);
        String intervalMsg = "Неверно задан интервал значений";
        while (result.indexOf("..") != -1) {
            int index = result.indexOf("..");
            String start = result.substring(0, index);
            String end = result.substring(index + 2);
            int first = start.indexOf("(");
            int last = end.lastIndexOf(")");
            if (first == -1 || last == -1)
                throw new ScriptException(intervalMsg);
            start = start.substring(first + 1);
            end = end.substring(0, last);
            if (isListInvalid(end))
                throw new ScriptException(intervalMsg);
            String[] pair = end.split(",");
            if (pair.length != 2)
                throw new ScriptException(intervalMsg);
            String step = pair[1];
            end = pair[0];
            Object[] vars = new Object[] {
                    engine.get("tempVarN1"),
                    engine.get("tmpVarAns1"),
                    engine.get("tmpVarI1"),
                    engine.get("tmpVarRes1")};
            String script = "1-" + step + ";" +
                    "tempVarN1 = Math.floor(Math.abs((" + end + ") - (" + start + "))/" + step +
                    ") + 1; tmpVarAns1 = []; for (tmpVarI1 = 0; tmpVarI1 < tempVarN1; tmpVarI1++) " +
                    "{tmpVarAns1.push((" + start + ") + tmpVarI1 * (" + step + ")); }; tmpVarRes1 = " +
                    "tmpVarAns1[Math.floor(Math.random() * (tmpVarAns1.length + 1))];";
            String res = "null";
            while (res.equals("null")) {
                engine.eval(script);
                res = String.valueOf(engine.get("tmpVarRes1"));
            }
            engine.put("tempVarN1", vars[0]);
            engine.put("tmpVarAns1", vars[1]);
            engine.put("tmpVarI1", vars[2]);
            engine.put("tmpVarRes1", vars[3]);
            result.replace(first, index + last + 3, res);
        }
        return result.toString();
    }

    private String parseMathExpr(String input) {
        StringBuilder result = new StringBuilder(input);
        for (Map.Entry<String, String> func : maths.entrySet()) {
            int len = func.getKey().length();
            int first = 0;
            while (result.indexOf(func.getKey(), first) != -1) {
                first = result.indexOf(func.getKey(), first);
                if (first == 0 || result.substring(first - 1, first).matches("\\W")) {
                    result.replace(first, first + len, func.getValue());
                    first += 5;
                }
                first++;
            }
        }
        return result.toString();
    }
}
