package ru.spbu.distolymp.validation.admin.tasks.validator;

import ru.spbu.distolymp.common.tasks.TaskEvaluator;
import ru.spbu.distolymp.validation.admin.tasks.annotation.Variables;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Vladislav Konovalov
 */
public class VariablesValidator implements ConstraintValidator<Variables, String> {
    @Override
    public void initialize(Variables variables) {
        // Do nothing because Variables annotation has no parameters
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        if (str == null || str.trim().equals("")) return true;
        TaskEvaluator evaluator = new TaskEvaluator(str);
        return !evaluator.getVariableMap().isEmpty();
    }
}
