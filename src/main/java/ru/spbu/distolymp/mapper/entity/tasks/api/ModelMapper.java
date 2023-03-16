package ru.spbu.distolymp.mapper.entity.tasks.api;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.tasks.ModelDto;
import ru.spbu.distolymp.entity.tasks.Model;

/**
 * @author Vladislav Konovalov
 */
@Mapper
public interface ModelMapper {
    ModelDto toDto(Model model);
    Model toEntity(ModelDto modelDto);
}
