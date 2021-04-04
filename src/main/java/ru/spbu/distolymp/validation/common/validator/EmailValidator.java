package ru.spbu.distolymp.validation.common.validator;

import lombok.RequiredArgsConstructor;
import ru.spbu.distolymp.validation.common.annotation.Email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Vladislav Konovalov
 */
@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public void initialize(Email email) {
        // Do nothing because Email annotation has no parameters
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email.matches("\\S+@\\S+\\.\\S+");
    }

}
