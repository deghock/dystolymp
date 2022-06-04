package ru.spbu.distolymp.service.admin.directories.institutes.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.entity.education.InstituteDto;

public interface InstituteService {

    void fillShowAllInstitutesModelMap(ModelMap modelMap);

    void fillAddNewInstituteModelMap(ModelMap modelMap);

    void fillShowEditPageModelMap(ModelMap modelMap, Long id);

    void saveOrEdit(InstituteDto instituteDto);

    InstituteDto getNewInstituteDto();

    InstituteDto getInstituteDtoById(Long id);

    void orderUp(Long id);

    void orderDown(Long id);

    void deleteInstitutesById(Long[] ids);

}
