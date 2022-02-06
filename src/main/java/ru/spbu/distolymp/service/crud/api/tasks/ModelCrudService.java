package ru.spbu.distolymp.service.crud.api.tasks;

import org.springframework.data.domain.Sort;
import ru.spbu.distolymp.dto.admin.models.ModelListDto;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface ModelCrudService {
    List<ModelListDto> getModels(Sort sort);
}
