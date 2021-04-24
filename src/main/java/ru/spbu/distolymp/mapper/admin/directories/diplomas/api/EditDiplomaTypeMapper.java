package ru.spbu.distolymp.mapper.admin.directories.diplomas.api;

import ru.spbu.distolymp.dto.admin.directories.diplomas.EditDiplomaTypeDto;
import ru.spbu.distolymp.entity.diploma.DiplomaType;

/**
 * @author Daria Usova
 */
public interface EditDiplomaTypeMapper {

    EditDiplomaTypeDto toDto(DiplomaType diplomaType);

}