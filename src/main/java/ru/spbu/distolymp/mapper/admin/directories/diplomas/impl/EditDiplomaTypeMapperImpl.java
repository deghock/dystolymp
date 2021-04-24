package ru.spbu.distolymp.mapper.admin.directories.diplomas.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.spbu.distolymp.dto.admin.directories.diplomas.EditDiplomaTypeDto;
import ru.spbu.distolymp.entity.diploma.DiplomaType;
import ru.spbu.distolymp.entity.enumeration.Orientation;
import ru.spbu.distolymp.mapper.admin.directories.diplomas.api.EditDiplomaTypeMapper;
import ru.spbu.distolymp.service.crud.api.diploma.DiplomaImageService;

import java.util.Base64;

/**
 * @author Daria Usova
 */
@Component
@RequiredArgsConstructor
public class EditDiplomaTypeMapperImpl implements EditDiplomaTypeMapper {

    private final DiplomaImageService diplomaImageService;

    @Override
    public EditDiplomaTypeDto toDto(DiplomaType diplomaType) {
        if (diplomaType == null) {
            return null;
        }

        EditDiplomaTypeDto editDiplomaTypeDto = new EditDiplomaTypeDto();
        editDiplomaTypeDto.setId( diplomaType.getId() );
        editDiplomaTypeDto.setNewName( diplomaType.getName() );
        editDiplomaTypeDto.setPortraitOrientation( orientationToBoolean(diplomaType.getOrientation()) );

        String oldImageFileName = diplomaType.getImageFileName();
        if (!oldImageFileName.trim().isEmpty()) {
            editDiplomaTypeDto.setOldImage( imageNameToImageString(diplomaType.getImageFileName()) );
        }

        return editDiplomaTypeDto;
    }

    private String imageNameToImageString(String imageFileName) {
        byte[] image = diplomaImageService.getImageWithName(imageFileName);
        return Base64.getEncoder().encodeToString(image);
    }

    private boolean orientationToBoolean(Orientation orientation) {
        return orientation == Orientation.PORTRAIT;
    }

}