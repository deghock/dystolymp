package ru.spbu.distolymp.service.admin.models.impl;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.common.files.FileNameGenerator;
import ru.spbu.distolymp.common.files.FileUtils;
import ru.spbu.distolymp.common.tasks.PointParser;
import ru.spbu.distolymp.dto.admin.models.ModelFilter;
import ru.spbu.distolymp.dto.admin.models.ModelListDto;
import ru.spbu.distolymp.dto.entity.tasks.ModelDto;
import ru.spbu.distolymp.entity.tasks.Model;
import ru.spbu.distolymp.exception.common.ResourceNotFoundException;
import ru.spbu.distolymp.mapper.admin.models.api.ModelListMapper;
import ru.spbu.distolymp.mapper.entity.tasks.ModelMapper;
import ru.spbu.distolymp.repository.tasks.ModelRepository;
import ru.spbu.distolymp.service.admin.models.api.ModelService;
import ru.spbu.distolymp.service.crud.api.lists.ListingProblemCrudService;
import ru.spbu.distolymp.service.crud.impl.tasks.ModelCrudServiceImpl;
import ru.spbu.distolymp.util.admin.tasks.ModelSpecsConverter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vladislav Konovalov
 */
@Service
public class ModelServiceImpl extends ModelCrudServiceImpl implements ModelService {
    private static final Sort SORT_BY_ID_DESC = Sort.by("id").descending();
    private static final String MODELS_PARAM = "modelList";
    private static final String BARSIC_FILE_EXTENSION = ".brc";

    public ModelServiceImpl(ModelRepository modelRepository,
                            ModelListMapper modelListMapper,
                            ListingProblemCrudService listingProblemCrudService,
                            ModelMapper modelMapper) {
        super(modelRepository, modelListMapper, listingProblemCrudService, modelMapper);
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

    @Override
    @Transactional
    public void updateModel(ModelDto modelDto) {
        Model model = modelMapper.toEntity(modelDto);
        initFields(model);

        MultipartFile image = modelDto.getImage();
        MultipartFile barsicFile = modelDto.getBarsicFile();
        String oldImageName = model.getImageFileName();
        String oldBarsicFileName = model.getBarsicFileName();

        boolean newImageUpload = !"".equals(image.getOriginalFilename());
        boolean newBarsicFileUpload = !"".equals(barsicFile.getOriginalFilename());
        boolean oldImageExists = (oldImageName != null) && (!"".equals(oldImageName));
        boolean oldBarsicFileExists = (oldBarsicFileName != null) && (!"".equals(oldBarsicFileName));
        boolean deleteImage = modelDto.isDeleteImage();

        Map<String, byte[]> filesWithNames = new HashMap<>();
        if (newImageUpload && !deleteImage) {
            String imageExtension = FileUtils.getImageExtension(image);
            String newImageName = FileNameGenerator.generateFileName(imageExtension);
            model.setImageFileName(newImageName);
            filesWithNames.put(newImageName, FileUtils.getFileBytes(image));
        }
        if (newBarsicFileUpload) {
            String newBarsicFileName = FileNameGenerator.generateFileName(BARSIC_FILE_EXTENSION);
            model.setBarsicFileName(newBarsicFileName);
            filesWithNames.put(newBarsicFileName, FileUtils.getFileBytes(barsicFile));
        }
        if (deleteImage) model.setImageFileName(null);

        saveOrUpdate(model, filesWithNames);

        if ((newImageUpload && oldImageExists) || deleteImage)
            fileService.deleteFile(oldImageName);
        if (newBarsicFileUpload && oldBarsicFileExists)
            fileService.deleteFile(oldBarsicFileName);
    }

    private void initFields(Model model) {
        if (model.getId() == null) model.setStatus(1);
        model.setType(1);
        if (model.getWidth() == null) model.setWidth(0);
        if (model.getHeight() == null) model.setHeight(0);
        model.setMaxPoints(PointParser.calculatePoints(model.getGradePoints()));
    }

    @Override
    @Transactional
    public void deleteModelWithFiles(Long id) {
        Model model = getModelById(id).orElseThrow(ResourceNotFoundException::new);
        deleteModelById(id);
        String imageName = model.getImageFileName();
        String barsicFileName = model.getBarsicFileName();
        boolean imageExists = (imageName != null) && (!"".equals(imageName));
        boolean barsicFileExists = (barsicFileName != null) && (!"".equals(barsicFileName));
        if (imageExists) fileService.deleteFile(imageName);
        if (barsicFileExists) fileService.deleteFile(barsicFileName);
    }

    @Override
    @Transactional
    public void copyModel(ModelListDto modelTitleDto) {
        Model model = getModelById(modelTitleDto.getId())
                .map(modelMapper::toDto)
                .map(modelMapper::toEntity)
                .orElseThrow(ResourceNotFoundException::new);
        model.setId(null);
        model.setTitle(modelTitleDto.getTitle());
        model.setType(1);
        model.setStatus(1);
        model.setMaxPoints(PointParser.calculatePoints(model.getGradePoints()));

        String imageName = model.getImageFileName();
        String barsicFileName = model.getBarsicFileName();
        boolean imageExists = (imageName != null) && (!"".equals(imageName));
        boolean barsicFileExists = (barsicFileName != null) && (!"".equals(barsicFileName));
        Map<String, byte[]> filesWithNames = new HashMap<>();
        if (imageExists) {
            String imageExtension = FileUtils.getExtensionFromFileName(imageName);
            String newImageName = FileNameGenerator.generateFileName(imageExtension);
            model.setImageFileName(newImageName);
            filesWithNames.put(newImageName, fileService.getFileWithName(imageName));
        }
        if (barsicFileExists) {
            String newBarsicFileName = FileNameGenerator.generateFileName(BARSIC_FILE_EXTENSION);
            model.setBarsicFileName(newBarsicFileName);
            filesWithNames.put(newBarsicFileName, fileService.getFileWithName(barsicFileName));
        }

        saveOrUpdate(model, filesWithNames);
    }

    @Override
    @Transactional
    public void unarchiveModel(Long id) {
        Model model = getModelById(id).orElseThrow(ResourceNotFoundException::new);
        model.setStatus(1);
        saveOrUpdate(model, new HashMap<>());
    }
}
