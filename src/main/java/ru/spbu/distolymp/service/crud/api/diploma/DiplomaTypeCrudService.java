package ru.spbu.distolymp.service.crud.api.diploma;

import ru.spbu.distolymp.dto.entity.diploma.DiplomaTypeDto;
import ru.spbu.distolymp.entity.diploma.DiplomaType;

import java.util.List;

/**
 * @author Daria Usova
 */
public interface DiplomaTypeCrudService {

    List<DiplomaTypeDto> getAllDiplomaTypes();

    boolean diplomaTypeWithNameExists(String name);

    void save(DiplomaType diplomaType, byte[] picture, String path);

}
