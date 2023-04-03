package ru.spbu.distolymp.service.crud.impl.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.common.files.FileService;
import ru.spbu.distolymp.dto.admin.models.ModelListDto;
import ru.spbu.distolymp.entity.tasks.Model;
import ru.spbu.distolymp.entity.tasks.Problem;
import ru.spbu.distolymp.exception.common.TechnicalException;
import ru.spbu.distolymp.mapper.admin.models.api.ModelListMapper;
import ru.spbu.distolymp.mapper.entity.tasks.api.ModelMapper;
import ru.spbu.distolymp.repository.tasks.ModelRepository;
import ru.spbu.distolymp.service.crud.api.lists.ListingProblemCrudService;
import ru.spbu.distolymp.service.crud.api.tasks.ModelCrudService;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@Primary
@RequiredArgsConstructor
public class ModelCrudServiceImpl implements ModelCrudService {
    private static final String SAVE_OR_UPDATE_PARAM = "An error occurred while saving or updating a model";
    private final ModelRepository modelRepository;
    private final ModelListMapper modelListMapper;
    private final ListingProblemCrudService listingProblemCrudService;
    protected final ModelMapper modelMapper;
    @Autowired
    @Qualifier("modelFileService")
    protected FileService fileService;

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

    @Override
    @Transactional
    public void saveOrUpdate(Model model, Map<String, byte[]> filesWithNames) {
        for (Map.Entry<String, byte[]> fileWithName : filesWithNames.entrySet()) {
            String fileName = fileWithName.getKey();
            byte[] file = fileWithName.getValue();
            if ("".equals(fileName)) continue;
            boolean fileSaved = fileService.saveFile(file, fileName);
            if (!fileSaved) {
                fileService.deleteFiles(filesWithNames.keySet());
                throw new TechnicalException("Model's file with name=" + fileName + " is not saved");
            }
        }
        try {
            modelRepository.save(model);
        } catch (DataAccessException e) {
            log.error(SAVE_OR_UPDATE_PARAM, e);
            fileService.deleteFiles(filesWithNames.keySet());
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional
    public void deleteModelById(Long id) {
        try {
            Model model = getModelById(id).orElseThrow(EntityNotFoundException::new);
            listingProblemCrudService.deleteByProblemId(id);
            modelRepository.delete(model);
        } catch (DataAccessException | EntityNotFoundException e) {
            log.error("An error occurred while deleting a model with id=" + id, e);
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional
    public Problem copyFromProblem(Long copyId, Problem problem) {
        try{
            Model copyModel = modelRepository.findFirstById(copyId).copyFromProblem(problem);
            modelRepository.save(copyModel);
            return copyModel;
        }catch (Exception e){
            throw new TechnicalException();
        }
    }
}
