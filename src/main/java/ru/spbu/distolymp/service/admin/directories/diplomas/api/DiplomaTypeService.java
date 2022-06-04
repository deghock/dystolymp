package ru.spbu.distolymp.service.admin.directories.diplomas.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.diplomas.EditDiplomaTypeDto;
import ru.spbu.distolymp.dto.admin.directories.diplomas.NewDiplomaTypeDto;

/**
 * @author Daria Usova
 */
public interface DiplomaTypeService {

    void fillShowAllDiplomaTypesModelMap(ModelMap modelMap);

    NewDiplomaTypeDto getNewDiplomaType();

    void addNewDiplomaType(NewDiplomaTypeDto newDiplomaTypeDto);

    void fillEditDiplomaModelMap(Integer diplomaTypeId, ModelMap modelMap);

    void updateDiplomaType(EditDiplomaTypeDto editDiplomaTypeDto);

}