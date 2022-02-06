package ru.spbu.distolymp.service.crud.impl.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.admin.models.ModelListDto;
import ru.spbu.distolymp.entity.tasks.Model;
import ru.spbu.distolymp.exception.common.TechnicalException;
import ru.spbu.distolymp.mapper.admin.models.api.ModelListMapper;
import ru.spbu.distolymp.mapper.entity.tasks.ModelMapper;
import ru.spbu.distolymp.repository.tasks.ModelRepository;
import ru.spbu.distolymp.service.crud.api.tasks.ModelCrudService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@RequiredArgsConstructor
public class ModelCrudServiceImpl implements ModelCrudService {
    private final ModelRepository modelRepository;
    private final ModelListMapper modelListMapper;
    protected final ModelMapper modelMapper;

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

    @Override
    @Transactional(readOnly = true)
    public List<ModelListDto> getModels(Sort sort, Specification<Model> spec) {
        try {
            List<Model> modelList = modelRepository.findAll(spec, sort);
            return modelListMapper.toDtoList(modelList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting models by specs", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Model> getModelById(Long id) {
        if (id == null) return Optional.empty();
        try {
            return modelRepository.findById(id);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting a model with id=" + id, e);
            throw new TechnicalException();
        }
    }
}
