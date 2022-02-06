package ru.spbu.distolymp.service.crud.api.tasks;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.models.ModelListDto;
import ru.spbu.distolymp.entity.tasks.Model;
import java.util.List;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
public interface ModelCrudService {
    List<ModelListDto> getModels(Sort sort);
    List<ModelListDto> getModels(Sort sort, Specification<Model> spec);
    Optional<Model> getModelById(Long id);
}
