package ru.spbu.distolymp.service.admin.models.api;

import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.models.ModelFilter;
import ru.spbu.distolymp.dto.entity.tasks.ModelDto;

/**
 * @author Vladislav Konovalov
 */
public interface ModelService {
    void fillShowAllModelModelMap(ModelMap modelMap);
    void fillShowAllModelModelMap(ModelMap modelMap, ModelFilter modelFilter);
    void fillShowEditPageModelMap(Long id, ModelMap modelMap);
    void fillShowAddPageModelMap(ModelMap modelMap);
    void addModel(ModelDto modelDto);
    void updateModel(ModelDto modelDto);
}
