package ru.spbu.distolymp.mapper.entity.division;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.division.DivisionDto;
import ru.spbu.distolymp.entity.division.Division;

import java.util.List;

/**
 * @author Daria Usova
 */
@Mapper
public interface DivisionMapper {

    DivisionDto toDto(Division division);

    List<DivisionDto> toDtoList(List<Division> divisionList);

}
