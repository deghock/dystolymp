package ru.spbu.distolymp.common.tasks;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;

/**
 * @author Vladislav Konovalov
 */
public class TaskParser {
    private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("Nashorn");
    private static final Map<String, String> maths = new HashMap<>();

    private TaskParser() {}

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

    public static List<String> getVarNames(String input) {
        List<String> lines = parseTaskConditionToLines(input);
        List<String> varNames = new ArrayList<>();
        for (String line : lines) {
            String varName = line.split("=")[0].trim();
            varNames.add(varName);
        }
        return varNames;
    }

    public static List<String> parseTaskConditionToLines(String condition) {
        List<String> lines = new ArrayList<>();
        if (condition == null) return lines;
        List<Integer> equalIndexes = new ArrayList<>();
        List<Integer> separatorIndexes = new ArrayList<>();
        separatorIndexes.add(-1);
        if (!condition.contains("=")) return lines;
        for (int i = condition.indexOf("="); i > -1; i = condition.indexOf("=", i + 1))
            equalIndexes.add(i);
        for (int i = 0; i < equalIndexes.size() - 1; i++) {
            String substr = condition.substring(equalIndexes.get(i) + 1, equalIndexes.get(i + 1));
            int semicolonIndex = substr.lastIndexOf(";");
            separatorIndexes.add(equalIndexes.get(i) + semicolonIndex + 1);
        }
        separatorIndexes.add(condition.length() - 1);
        for (int i = 0; i < separatorIndexes.size() - 1; i++) {
            String substr = condition.substring(separatorIndexes.get(i) + 1, separatorIndexes.get(i + 1) + 1);
            if (substr.length() < 3) return new ArrayList<>();
            lines.add(substr);
        }
        return lines;
    }

    static String parseList(String input) throws ScriptException {
        StringBuilder parsedInput = new StringBuilder(input);
        String error = "Invalid list";
        while (parsedInput.indexOf("[") != -1 && parsedInput.indexOf("]") != -1) {
            int first = parsedInput.indexOf("[");
            int last = parsedInput.indexOf("]");
            if (last - first < 2)
                throw new ScriptException(error);
            String substr = parsedInput.substring(first + 1, last);
            if (isListInvalid(substr))
                throw new ScriptException(error);
            String[] valuesArray = substr.split(",");
            for (String value : valuesArray)
                if (value.trim().equals("")) throw new ScriptException(error);
            List<String> values = Arrays.asList(valuesArray);
            Collections.shuffle(values);
            parsedInput.replace(first, last + 1, values.get(0));
        }
        return parsedInput.toString();
    }

    private static boolean isListInvalid(String parsedList) {
        String str = parsedList.replaceAll("\\s", "");
        char[] chars = str.toCharArray();
        return chars[0] == ',' || chars[chars.length - 1] == ',';
    }

    static String parseInterval(String input) throws ScriptException {
        StringBuilder parsedInput = new StringBuilder(input);
        String error = "Invalid interval";
        while (parsedInput.indexOf("..") != -1) {
            int index = parsedInput.indexOf("..");
            String start = parsedInput.substring(0, index);
            String end = parsedInput.substring(index + 2);
            int first = start.indexOf("(");
            int last = end.lastIndexOf(")");
            if (first == -1 || last == -1)
                throw new ScriptException(error);
            start = start.substring(first + 1);
            end = end.substring(0, last);
            if (isListInvalid(end))
                throw new ScriptException(error);
            String[] pair = end.split(",");
            if (pair.length != 2)
                throw new ScriptException(error);
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
            parsedInput.replace(first, index + last + 3, res);
        }
        return parsedInput.toString();
    }

    static String parseMathExpr(String input) {
        StringBuilder parsedInput = new StringBuilder(input);
        for (Map.Entry<String, String> func : maths.entrySet()) {
            int len = func.getKey().length();
            int first = 0;
            while (parsedInput.indexOf(func.getKey(), first) != -1) {
                first = parsedInput.indexOf(func.getKey(), first);
                if (first == 0 || parsedInput.substring(first - 1, first).matches("\\W")) {
                    parsedInput.replace(first, first + len, func.getValue());
                    first += 5;
                }
                first++;
            }
        }
        return parsedInput.toString();
    }
}