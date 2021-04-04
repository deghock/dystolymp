package ru.spbu.distolymp.service.admin.directories.diplomas.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import ru.spbu.distolymp.dto.admin.directories.diplomas.NewDiplomaTypeDto;
import ru.spbu.distolymp.dto.entity.diploma.DiplomaTypeDto;
import ru.spbu.distolymp.entity.diploma.DiplomaType;
import ru.spbu.distolymp.exception.admin.directories.diplomas.DiplomaTypeServiceException;
import ru.spbu.distolymp.mapper.admin.directories.diplomas.NewDiplomaTypeMapper;
import ru.spbu.distolymp.mapper.entity.diploma.DiplomaTypeMapper;
import ru.spbu.distolymp.repository.diploma.DiplomaTypeRepository;
import ru.spbu.distolymp.service.admin.directories.diplomas.api.DiplomaTypeService;
import ru.spbu.distolymp.service.crud.impl.diploma.DiplomaTypeCrudServiceImpl;

import javax.servlet.http.HttpServletRequest;
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
    private static final String IMG_DIRECTORY_PATH = "/resources/img/diploms/";

    public DiplomaTypeServiceImpl(DiplomaTypeRepository diplomaTypeRepository, DiplomaTypeMapper diplomaTypeMapper,
                                  NewDiplomaTypeMapper newDiplomaTypeMapper) {
        super(diplomaTypeRepository, diplomaTypeMapper);
        this.newDiplomaTypeMapper = newDiplomaTypeMapper;
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
    public void addNewDiplomaType(NewDiplomaTypeDto newDiplomaTypeDto, HttpServletRequest httpRequest) {
        DiplomaType diplomaType = newDiplomaTypeMapper.toEntity(newDiplomaTypeDto);
        MultipartFile image = newDiplomaTypeDto.getImage();

        initDiplomaTypeFields(diplomaType, image);
        saveDiplomaTypeAndImage(diplomaType, image, httpRequest);
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

    private void saveDiplomaTypeAndImage(DiplomaType diplomaType, MultipartFile image, HttpServletRequest httpRequest) {
        String path = httpRequest.getServletContext().getRealPath(IMG_DIRECTORY_PATH);
        save(diplomaType, getImageBytes(image), path);
    }

}
