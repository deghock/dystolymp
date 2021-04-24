package ru.spbu.distolymp.validation.admin.directories.diplomas.annotation;

import ru.spbu.distolymp.validation.admin.directories.diplomas.validator.UniqueDiplomaTypeNameClassValidator;
import ru.spbu.distolymp.validation.admin.directories.diplomas.validator.UniqueDiplomaTypeNameFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Daria Usova
 */
@Documented
@Constraint(validatedBy = {UniqueDiplomaTypeNameFieldValidator.class,
        UniqueDiplomaTypeNameClassValidator.class})
@Target( {ElementType.FIELD, ElementType.TYPE} )
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueDiplomaTypeName {

    String message() default "{diploma.type.name.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
