package ru.spbu.distolymp.service.admin.directories.diplomas.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.directories.diplomas.NewDiplomaTypeDto;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Daria Usova
 */
public interface DiplomaTypeService {

    void fillShowAllDiplomaTypesModelMap(ModelMap modelMap);

    NewDiplomaTypeDto getNewDiplomaType();

    void addNewDiplomaType(NewDiplomaTypeDto newDiplomaTypeDto, HttpServletRequest httpRequest);

}