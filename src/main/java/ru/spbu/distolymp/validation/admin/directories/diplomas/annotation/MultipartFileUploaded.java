package ru.spbu.distolymp.validation.admin.directories.diplomas.annotation;

import ru.spbu.distolymp.validation.admin.directories.diplomas.validator.MultipartFileUploadedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Daria Usova
 */
@Documented
@Constraint(validatedBy = MultipartFileUploadedValidator.class)
@Target( {ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface MultipartFileUploaded {

    String message() default "{file.uploaded}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}