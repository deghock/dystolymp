package ru.spbu.distolymp.validation.admin.directories.diplomas.validator;

import lombok.RequiredArgsConstructor;
import ru.spbu.distolymp.dto.admin.directories.diplomas.EditDiplomaTypeDto;
import ru.spbu.distolymp.entity.diploma.DiplomaType;
import ru.spbu.distolymp.service.crud.api.diploma.DiplomaTypeCrudService;
import ru.spbu.distolymp.validation.admin.directories.diplomas.annotation.UniqueDiplomaTypeName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

/**
 * @author Daria Usova
 */
@RequiredArgsConstructor
public class UniqueDiplomaTypeNameClassValidator implements
        ConstraintValidator<UniqueDiplomaTypeName, EditDiplomaTypeDto> {

    private final DiplomaTypeCrudService diplomaTypeCrudService;

    @Override
    public void initialize(UniqueDiplomaTypeName constraintAnnotation) {
        // Do nothing because UniqueDiplomaTypeName annotation has no parameters
    }

    @Override
    public boolean isValid(EditDiplomaTypeDto diplomaTypeDto, ConstraintValidatorContext context) {
        if (diplomaTypeDto == null || diplomaTypeDto.getId() == null || diplomaTypeDto.getNewName() == null) {
            return true;
        }

        String newName = diplomaTypeDto.getNewName();
        Optional<DiplomaType> diplomaTypeFromDb = diplomaTypeCrudService.getDiplomaTypeById(diplomaTypeDto.getId());

        boolean checkNotRequired = diplomaTypeFromDb
                .map(DiplomaType::getName)
                .map(oldName -> oldName.equals(newName))
                .orElse(true);

        return checkNotRequired || !diplomaTypeCrudService.diplomaTypeWithNameExists(newName);
    }

}