package ru.spbu.distolymp.service.admin.directories.diplomas.impl;

import jdk.management.resource.ResourceRequestDeniedException;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.dto.admin.directories.diplomas.EditDiplomaTypeDto;
import ru.spbu.distolymp.dto.admin.directories.diplomas.NewDiplomaTypeDto;
import ru.spbu.distolymp.dto.entity.diploma.DiplomaTypeDto;
import ru.spbu.distolymp.entity.diploma.DiplomaType;
import ru.spbu.distolymp.entity.enumeration.Orientation;
import ru.spbu.distolymp.exception.admin.directories.diplomas.DiplomaTypeServiceException;
import ru.spbu.distolymp.exception.common.ResourceNotFoundException;
import ru.spbu.distolymp.mapper.admin.directories.diplomas.api.EditDiplomaTypeMapper;
import ru.spbu.distolymp.mapper.admin.directories.diplomas.api.NewDiplomaTypeMapper;
import ru.spbu.distolymp.mapper.entity.diploma.DiplomaTypeMapper;
import ru.spbu.distolymp.repository.diploma.DiplomaTypeRepository;
import ru.spbu.distolymp.service.admin.directories.diplomas.api.DiplomaTypeService;
import ru.spbu.distolymp.service.crud.api.diploma.DiplomaImageService;
import ru.spbu.distolymp.service.crud.impl.diploma.DiplomaTypeCrudServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Daria Usova
 */
@Log4j
@Service
@Primary
public class DiplomaTypeServiceImpl extends DiplomaTypeCrudServiceImpl implements DiplomaTypeService {

    private final NewDiplomaTypeMapper newDiplomaTypeMapper;
    private final EditDiplomaTypeMapper editDiplomaTypeMapper;

    public DiplomaTypeServiceImpl(DiplomaTypeRepository diplomaTypeRepository,
                                  DiplomaTypeMapper diplomaTypeMapper,
                                  DiplomaImageService diplomaImageService,
                                  NewDiplomaTypeMapper newDiplomaTypeMapper,
                                  EditDiplomaTypeMapper editDiplomaTypeMapper) {
        super(diplomaTypeRepository, diplomaTypeMapper, diplomaImageService);
        this.newDiplomaTypeMapper = newDiplomaTypeMapper;
        this.editDiplomaTypeMapper = editDiplomaTypeMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public void fillShowAllDiplomaTypesModelMap(ModelMap modelMap) {
        List<DiplomaTypeDto> diplomaTypes = getAllDiplomaTypes();
        List<Integer> idList = new ArrayList<>();

        modelMap.put("diplomaTypes", diplomaTypes);
        modelMap.put("idList", idList);
    }

    @Override
    public NewDiplomaTypeDto getNewDiplomaType() {
        NewDiplomaTypeDto diploma = new NewDiplomaTypeDto();
        diploma.setPortraitOrientation(false);

        return diploma;
    }

    @Override
    @Transactional
    public void addNewDiplomaType(NewDiplomaTypeDto newDiplomaTypeDto) {
        DiplomaType diplomaType = newDiplomaTypeMapper.toEntity(newDiplomaTypeDto);
        MultipartFile image = newDiplomaTypeDto.getImage();

        initDiplomaTypeFields(diplomaType, image);
        saveDiplomaTypeAndImage(diplomaType, image);
    }

    private void initDiplomaTypeFields(DiplomaType diplomaType, MultipartFile image) {
        diplomaType.setSubtitle("");
        diplomaType.setPrintImage(true);

        String fileName = getImageFileName(image);
        diplomaType.setImageFileName(fileName);
    }

    private String getImageFileName(MultipartFile multipartFile) {
        String extension = getExtension(multipartFile);
        return generateFileName() + extension;
    }

    private String getExtension(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.getContentType() == null) {
            throw new IllegalArgumentException();
        }

        return "." + multipartFile.getContentType().replaceFirst("image/", "");
    }

    private String generateFileName() {
        StringBuilder stringBuilder = new StringBuilder();
        int aCharIndex = 97;
        int zCharIndex = 122;

        for (int i = 1; i <= 20; i++) {
            stringBuilder.append((char) ThreadLocalRandom.current().nextInt(aCharIndex, zCharIndex + 1));
        }

        return stringBuilder.toString();
    }

    private byte[] getImageBytes(MultipartFile image) {
        try {
            return image.getBytes();
        } catch (IOException e) {
            log.error("An error occurred while getting bytes of multipart diploma type image", e);
            throw new DiplomaTypeServiceException();
        }
    }

    private void saveDiplomaTypeAndImage(DiplomaType diplomaType, MultipartFile image) {
        save(diplomaType, getImageBytes(image));
    }

    @Override
    @Transactional(readOnly = true)
    public void fillEditDiplomaModelMap(Integer diplomaTypeId, ModelMap modelMap) {
        EditDiplomaTypeDto diploma = getEditDiplomaTypeDtoById(diplomaTypeId);

        modelMap.put("diploma", diploma);
    }

    private EditDiplomaTypeDto getEditDiplomaTypeDtoById(Integer id) {
        if (id == null) {
            throw new ResourceNotFoundException();
        }

        return getDiplomaTypeById(id)
                .map(editDiplomaTypeMapper::toDto)
                .orElseThrow(ResourceRequestDeniedException::new);
    }

    @Override
    @Transactional
    public void updateDiplomaType(EditDiplomaTypeDto editDiplomaTypeDto) {
        DiplomaType diplomaType = getDiplomaTypeById(editDiplomaTypeDto.getId())
                .orElseThrow(DiplomaTypeServiceException::new);

        diplomaType.setName(editDiplomaTypeDto.getNewName());
        diplomaType.setOrientation( editDiplomaTypeDto.isPortraitOrientation() ? Orientation.PORTRAIT : Orientation.LANDSCAPE );

        MultipartFile image = editDiplomaTypeDto.getNewImage();
        boolean newImageUploaded = !MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE.equals(image.getContentType());

        if (newImageUploaded) {
            String newImageFileName = updateDiplomaTypeImage(diplomaType, image);
            diplomaType.setImageFileName(newImageFileName);
            diplomaType.setPrintImage(true);
        }
    }

    private String updateDiplomaTypeImage(DiplomaType diplomaType, MultipartFile image) {
        String newImageFileName = saveNewImage(image);
        String oldImageFileName = diplomaType.getImageFileName();

        deleteOldImage(oldImageFileName);
        return newImageFileName;
    }

    private String saveNewImage(MultipartFile image) {
        String newImageFileName = getImageFileName(image);
        boolean newImageSaved = diplomaImageService.saveDiplomaTypeImage(getImageBytes(image), newImageFileName);

        if (!newImageSaved) {
            throw new DiplomaTypeServiceException();
        }

        return newImageFileName;
    }

    private void deleteOldImage(String oldImageFileName) {
        if (oldImageFileName == null || oldImageFileName.trim().isEmpty()) {
            return;
        }

        diplomaImageService.deleteDiplomaTypeImage(oldImageFileName);
    }

}
