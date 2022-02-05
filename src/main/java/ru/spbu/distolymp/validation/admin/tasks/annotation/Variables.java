package ru.spbu.distolymp.validation.admin.tasks.annotation;

import ru.spbu.distolymp.validation.admin.tasks.validator.VariablesValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Vladislav Konovalov
 */
@Documented
@Constraint(validatedBy = VariablesValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Variables {
    String message() default "{variables.pattern}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
