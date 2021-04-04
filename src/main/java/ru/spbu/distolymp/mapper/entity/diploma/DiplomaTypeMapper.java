package ru.spbu.distolymp.mapper.entity.diploma;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.diploma.DiplomaTypeDto;
import ru.spbu.distolymp.entity.diploma.DiplomaType;

import java.util.List;

/**
 * @author Daria Usova
 */
@Mapper
public interface DiplomaTypeMapper {

    DiplomaTypeDto toDto(DiplomaType diplomaType);

    List<DiplomaTypeDto> toDtoList(List<DiplomaType> diplomaTypeList);

}