package ru.spbu.distolymp.validation.admin.directories.diplomas.validator;

import lombok.RequiredArgsConstructor;
import ru.spbu.distolymp.service.crud.api.diploma.DiplomaTypeCrudService;
import ru.spbu.distolymp.validation.admin.directories.diplomas.annotation.UniqueDiplomaTypeName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Daria Usova
 */
@RequiredArgsConstructor
public class UniqueDiplomaTypeNameValidator implements ConstraintValidator<UniqueDiplomaTypeName, String> {

    private final DiplomaTypeCrudService diplomaTypeCrudService;

    @Override
    public void initialize(UniqueDiplomaTypeName constraintAnnotation) {
        // Do nothing because UniqueDiplomaTypeName annotation has no parameters
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return true;
        }

        return !diplomaTypeCrudService.diplomaTypeWithNameExists(name.trim());
    }

}
