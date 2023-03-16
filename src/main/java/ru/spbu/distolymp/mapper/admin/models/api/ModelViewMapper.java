package ru.spbu.distolymp.mapper.admin.models.api;

import ru.spbu.distolymp.dto.admin.models.ModelViewDto;
import ru.spbu.distolymp.entity.tasks.Model;

/**
 * @author Vladislav Konovalov
 */
public interface ModelViewMapper {
    ModelViewDto toDto(Model model);
    ModelViewDto toDto(Model model, String params);
}
