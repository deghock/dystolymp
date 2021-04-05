package ru.spbu.distolymp.validation.common.annotation;

import ru.spbu.distolymp.validation.common.validator.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Vladislav Konovalov
 */
@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {

    String message() default "{email.pattern}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
