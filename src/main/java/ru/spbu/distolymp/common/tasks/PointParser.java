package ru.spbu.distolymp.common.tasks;

import java.util.ArrayList;
import java.util.Collections;
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
            return Collections.emptyList();
        String[] pointsArray = pointsInput.split(";");
        for (String pointStr : pointsArray) {
            pointStr = pointStr.trim();
            if (pointStr.equals("")) return Collections.emptyList();
            double point;
            try {
                point = Double.parseDouble(pointStr);
            } catch (NumberFormatException e) {
                return  Collections.emptyList();
            }
            if (point < 0.0) return Collections.emptyList();
            points.add(pointStr);
        }
        return points;
    }
}
