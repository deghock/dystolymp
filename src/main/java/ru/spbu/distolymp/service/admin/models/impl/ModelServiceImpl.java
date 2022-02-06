package ru.spbu.distolymp.service.admin.models.impl;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.models.ModelFilter;
import ru.spbu.distolymp.dto.admin.models.ModelListDto;
import ru.spbu.distolymp.dto.entity.tasks.ModelDto;
import ru.spbu.distolymp.entity.tasks.Model;
import ru.spbu.distolymp.exception.common.ResourceNotFoundException;
import ru.spbu.distolymp.mapper.admin.models.api.ModelListMapper;
import ru.spbu.distolymp.mapper.entity.tasks.ModelMapper;
import ru.spbu.distolymp.repository.tasks.ModelRepository;
import ru.spbu.distolymp.service.admin.models.api.ModelService;
import ru.spbu.distolymp.service.crud.impl.tasks.ModelCrudServiceImpl;
import ru.spbu.distolymp.util.admin.tasks.ModelSpecsConverter;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Service
public class ModelServiceImpl extends ModelCrudServiceImpl implements ModelService {
    private static final Sort SORT_BY_ID_DESC = Sort.by("id").descending();
    private static final String MODELS_PARAM = "modelList";

    public ModelServiceImpl(ModelRepository modelRepository,
                            ModelListMapper modelListMapper,
                            ModelMapper modelMapper) {
        super(modelRepository, modelListMapper, modelMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllModelModelMap(ModelMap modelMap) {
        List<ModelListDto> modelDtoList = getModels(SORT_BY_ID_DESC);
        modelMap.put(MODELS_PARAM, modelDtoList);
        modelMap.put("modelForCopy", new ModelListDto());
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllModelModelMap(ModelMap modelMap, ModelFilter modelFilter) {
        Specification<Model> specs = ModelSpecsConverter.toSpecs(modelFilter);
        List<ModelListDto> modelDtoList;
        if (specs == null)
            modelDtoList = getModels(SORT_BY_ID_DESC);
        else
            modelDtoList = getModels(SORT_BY_ID_DESC, specs);
        modelMap.put(MODELS_PARAM, modelDtoList);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowEditPageModelMap(Long id, ModelMap modelMap) {
        ModelDto modelDto = getModelById(id)
                .map(modelMapper::toDto)
                .orElseThrow(ResourceNotFoundException::new);
        modelMap.put("model", modelDto);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAddPageModelMap(ModelMap modelMap) {
        ModelDto modelDto = new ModelDto();
        modelDto.setWidth(0);
        modelDto.setHeight(0);
        modelMap.put("model", modelDto);
    }
}
