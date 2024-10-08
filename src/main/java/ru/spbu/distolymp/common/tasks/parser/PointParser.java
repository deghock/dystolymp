package ru.spbu.distolymp.common.tasks.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public class PointParser {
    private PointParser() {}

    public static List<String> parsePoints(String pointsInput) {
        List<String> points = new ArrayList<>();
        if (pointsInput == null) return points;
        pointsInput = pointsInput.trim();
        if (pointsInput.toCharArray()[0] == ';')
            return new ArrayList<>();
        String[] pointsArray = pointsInput.split(";");
        for (String pointStr : pointsArray) {
            pointStr = pointStr.trim();
            if (pointStr.equals("")) return new ArrayList<>();
            double point;
            try {
                point = Double.parseDouble(pointStr);
            } catch (NumberFormatException e) {
                return  new ArrayList<>();
            }
            if (point < 0.0) return new ArrayList<>();
            points.add(pointStr);
        }
        return points;
    }

    public static Double calculatePoints(String pointsInput) {
        List<String> points = parsePoints(pointsInput);
        double result = 0.0;
        for (String point : points)
            result += Double.parseDouble(point);
        return result;
    }
}
