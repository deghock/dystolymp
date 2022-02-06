package ru.spbu.distolymp.service.admin.models.impl;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import ru.spbu.distolymp.dto.admin.models.ModelFilter;
import ru.spbu.distolymp.dto.admin.models.ModelListDto;
import ru.spbu.distolymp.entity.tasks.Model;
import ru.spbu.distolymp.mapper.admin.models.api.ModelListMapper;
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
                            ModelListMapper modelListMapper) {
        super(modelRepository, modelListMapper);
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
}
