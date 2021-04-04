package ru.spbu.distolymp.mapper.admin.directories.diplomas;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.spbu.distolymp.dto.admin.directories.diplomas.NewDiplomaTypeDto;
import ru.spbu.distolymp.entity.diploma.DiplomaType;
import ru.spbu.distolymp.entity.enumeration.Orientation;

/**
 * @author Daria Usova
 */
@Mapper
public interface NewDiplomaTypeMapper {

    @Mapping(target = "orientation", source = "portraitOrientation")
    DiplomaType toEntity(NewDiplomaTypeDto newDiplomaTypeDto);

    default Orientation toOrientation(boolean portraitOrientation) {
        return portraitOrientation ? Orientation.PORTRAIT : Orientation.LANDSCAPE;
    }
}