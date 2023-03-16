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
import ru.spbu.distolymp.service.crud.api.diploma.DiplomaImageService;
import ru.spbu.distolymp.service.crud.api.diploma.DiplomaTypeCrudService;

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

    protected final DiplomaTypeRepository diplomaTypeRepository;
    private final DiplomaTypeMapper diplomaTypeMapper;
    protected final DiplomaImageService diplomaImageService;

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
    public void save(DiplomaType diplomaType, byte[] image) {
        String imageFileName = diplomaType.getImageFileName();
        boolean fileIsSaved = diplomaImageService.saveDiplomaTypeImage(image, imageFileName);

        if (!fileIsSaved) {
            throw new DiplomaTypeCrudServiceException();
        }

        try {
            diplomaTypeRepository.save(diplomaType);
        } catch (DataAccessException e) {
            log.error("An error occurred while saving diploma type", e);
            diplomaImageService.deleteDiplomaTypeImage(imageFileName);

            throw new DiplomaTypeCrudServiceException();
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

    @Override
    @Transactional(readOnly = true)
    public Optional<DiplomaType> getDiplomaTypeById(Integer id) {
        if (id == null) {
            return Optional.empty();
        }

        try {
            return diplomaTypeRepository.findById(id);
        } catch (DataAccessException e) {
            log.error("An error occurred while getting diploma by id=" + id, e);
            throw new DiplomaTypeCrudServiceException();
        }
    }

}