package ru.spbu.distolymp.service.crud.impl.diploma;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.dto.entity.diploma.DiplomaTypeDto;
import ru.spbu.distolymp.entity.diploma.DiplomaType;
import ru.spbu.distolymp.exception.crud.diploma.DiplomaTypeCrudServiceException;
import ru.spbu.distolymp.mapper.entity.diploma.DiplomaTypeMapper;
import ru.spbu.distolymp.repository.diploma.DiplomaTypeRepository;
import ru.spbu.distolymp.service.crud.api.diploma.DiplomaTypeCrudService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Daria Usova
 */
@Log4j
@Service
@RequiredArgsConstructor
public class DiplomaTypeCrudServiceImpl implements DiplomaTypeCrudService {

    private final DiplomaTypeRepository diplomaTypeRepository;
    private final DiplomaTypeMapper diplomaTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DiplomaTypeDto> getAllDiplomaTypes() {
        List<DiplomaTypeDto> diplomaTypesDtoList = new ArrayList<>();

        try {
            List<DiplomaType> diplomaTypesList = (List<DiplomaType>) diplomaTypeRepository.findAll();
            diplomaTypesDtoList = diplomaTypeMapper.toDtoList(diplomaTypesList);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting all diploma types", e);
        }

        return diplomaTypesDtoList;
    }

    @Override
    @Transactional
    public void save(DiplomaType diplomaType, byte[] image, String path) {
        boolean fileIsSaved = saveDiplomaTypeImage(image, path + diplomaType.getImageFileName());

        if (!fileIsSaved) {
            throw new DiplomaTypeCrudServiceException();
        }

        try {
            diplomaTypeRepository.save(diplomaType);
        } catch (DataAccessException e) {
            log.error("An error occurred while saving diploma type", e);
            deleteDiplomaTypeImage(path);

            throw new DiplomaTypeCrudServiceException();
        }
    }

    private boolean saveDiplomaTypeImage(byte[] bytes, String path) {
        File file = new File(path);

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(bytes);
        } catch (IOException e) {
            log.error("An error occurred while saving diploma type image", e);
            return false;
        }

        return true;
    }

    private void deleteDiplomaTypeImage(String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.delete(path);
        } catch (IOException e) {
            log.error("Diploma type image with name " + path + " not deleted", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean diplomaTypeWithNameExists(String name) {
        try {
            Optional<DiplomaType> diplomaTypeOptional = diplomaTypeRepository.findDiplomaTypeByName(name);
            return diplomaTypeOptional.isPresent();
        } catch (DataAccessException e) {
            log.error("An error occurred while finding diploma type by name");
            throw new DiplomaTypeCrudServiceException();
        }
    }

}