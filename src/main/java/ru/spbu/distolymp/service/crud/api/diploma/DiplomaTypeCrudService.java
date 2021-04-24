package ru.spbu.distolymp.service.crud.api.diploma;

import ru.spbu.distolymp.dto.entity.diploma.DiplomaTypeDto;
import ru.spbu.distolymp.entity.diploma.DiplomaType;

import java.util.List;
import java.util.Optional;

/**
 * @author Daria Usova
 */
public interface DiplomaTypeCrudService {

    List<DiplomaTypeDto> getAllDiplomaTypes();

    boolean diplomaTypeWithNameExists(String name);

    void save(DiplomaType diplomaType, byte[] picture);

    Optional<DiplomaType> getDiplomaTypeById(Integer id);

}
