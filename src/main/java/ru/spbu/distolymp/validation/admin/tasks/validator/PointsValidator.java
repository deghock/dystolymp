package ru.spbu.distolymp.validation.admin.tasks.validator;

import ru.spbu.distolymp.validation.admin.tasks.annotation.Points;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public class PointsValidator implements ConstraintValidator<Points, String> {
    @Override
    public void initialize(Points points) {
        // Do nothing because Points annotation has no parameters
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null || str.trim().equals("")) return true;
        return !parsePoints(str).isEmpty();
    }

    private List<String> parsePoints(String input) {
        List<String> points = new ArrayList<>();
        input = input.trim();
        if (input.toCharArray()[0] == ';' || input.toCharArray()[input.length() - 1] == ';')
            return Collections.emptyList();
        String[] pointsArray = input.split(";");
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
