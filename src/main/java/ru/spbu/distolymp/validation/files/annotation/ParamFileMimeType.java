package ru.spbu.distolymp.validation.files.annotation;

import ru.spbu.distolymp.validation.files.validator.ParamFileMimeTypeValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Vladislav Konovalov
 */
@Documented
@Constraint(validatedBy = ParamFileMimeTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamFileMimeType {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
