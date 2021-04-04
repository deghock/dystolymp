package ru.spbu.distolymp.validation.admin.directories.diplomas.annotation;

import ru.spbu.distolymp.validation.admin.directories.diplomas.validator.ImageMimeTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Daria Usova
 */
@Documented
@Constraint(validatedBy = ImageMimeTypeValidator.class)
@Target( {ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageMimeType {

    String message() default "{image.mime.type}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}