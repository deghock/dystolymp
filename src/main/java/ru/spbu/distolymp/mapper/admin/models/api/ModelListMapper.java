package ru.spbu.distolymp.mapper.admin.models.api;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.admin.models.ModelListDto;
import ru.spbu.distolymp.entity.tasks.Model;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Mapper
public interface ModelListMapper {
    ModelListDto toDto(Model model);
    List<ModelListDto> toDtoList(List<Model> modelList);

    default String integerToString(Integer status) {
        switch (status) {
            case 1:
                return "Новая";
            case 2:
                return "Опубликована";
            case 3:
                return "Архив";
            default:
                return "Неизвестен";
        }
    }
}
