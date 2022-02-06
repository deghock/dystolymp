package ru.spbu.distolymp.service.crud.impl.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.models.ModelListDto;
import ru.spbu.distolymp.entity.tasks.Model;
import ru.spbu.distolymp.mapper.admin.models.api.ModelListMapper;
import ru.spbu.distolymp.repository.tasks.ModelRepository;
import ru.spbu.distolymp.service.crud.api.tasks.ModelCrudService;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@RequiredArgsConstructor
public class ModelCrudServiceImpl implements ModelCrudService {
    private final ModelRepository modelRepository;
    private final ModelListMapper modelListMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ModelListDto> getModels(Sort sort) {
        try {
            List<Model> modelList = modelRepository.findByType(1, sort);
            return modelListMapper.toDtoList(modelList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting models", e);
            return new ArrayList<>();
        }
    }
}
