package ru.spbu.distolymp.validation.common.validator;

import ru.spbu.distolymp.validation.common.annotation.Email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Vladislav Konovalov
 */
public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public void initialize(Email email) {
        // Do nothing because Email annotation has no parameters
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null) {
            return true;
        } else {
            return email.matches("\\S+@\\S+\\.\\S+");
        }
    }

}
