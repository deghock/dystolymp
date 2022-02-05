package ru.spbu.distolymp.validation.admin.tasks.annotation;

import ru.spbu.distolymp.validation.admin.tasks.validator.PointsValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Vladislav Konovalov
 */
@Documented
@Constraint(validatedBy = PointsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Points {
    String message() default "{points.pattern}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
