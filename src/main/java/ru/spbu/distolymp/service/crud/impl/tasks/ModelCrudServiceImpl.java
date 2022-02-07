package ru.spbu.distolymp.service.crud.impl.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.common.files.ImageService;
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
    private static final String SAVE_OR_UPDATE_PARAM = "An error occurred while saving or updating a model";
    private final ModelRepository modelRepository;
    private final ModelListMapper modelListMapper;
    protected final ModelMapper modelMapper;
    @Autowired
    @Qualifier("modelImageService")
    protected ImageService imageService;

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
    public void saveOrUpdate(Model model, boolean deleteImage) {
        String imageName = model.getImageFileName();
        if (deleteImage) model.setImageFileName(null);
        try {
            modelRepository.save(model);
            if (deleteImage) imageService.deleteImage(imageName);
        } catch (DataAccessException e) {
            log.error(SAVE_OR_UPDATE_PARAM, e);
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional
    public void saveOrUpdate(Model model, byte[] image) {
        String imageName = model.getImageFileName();
        boolean imageSaved = imageService.saveImage(image, imageName);
        if (!imageSaved)
            throw new TechnicalException("Model image not saved");
        try {
            modelRepository.save(model);
        } catch (DataAccessException e) {
            log.error(SAVE_OR_UPDATE_PARAM, e);
            imageService.deleteImage(imageName);
            throw new TechnicalException();
        }
    }

    @Override
    @Transactional
    public void saveOrUpdate(Model model, byte[] image, String oldImageName, boolean deleteImage) {
        if (deleteImage) {
            model.setImageFileName(null);
            saveOrUpdate(model, false);
        } else {
            saveOrUpdate(model, image);
        }
        imageService.deleteImage(oldImageName);
    }
}
