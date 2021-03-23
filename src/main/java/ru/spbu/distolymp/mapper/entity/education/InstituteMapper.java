package ru.spbu.distolymp.mapper.entity.education;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.education.InstituteDto;
import ru.spbu.distolymp.entity.education.Institute;

import java.util.List;

@Mapper
public interface InstituteMapper {

    InstituteDto toDto(Institute institute);

    List<InstituteDto> toDtoList(List<Institute> userInstitutes);

    Institute toEntity(InstituteDto instituteDto);

}
