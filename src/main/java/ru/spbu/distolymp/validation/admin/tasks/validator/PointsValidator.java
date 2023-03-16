package ru.spbu.distolymp.validation.admin.tasks.validator;

import ru.spbu.distolymp.validation.admin.tasks.annotation.Points;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import static ru.spbu.distolymp.common.tasks.parser.PointParser.parsePoints;

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
}
