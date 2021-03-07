package ru.spbu.distolymp.mapper.entity.lists;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.entity.lists.DivisionDto;
import ru.spbu.distolymp.entity.lists.Division;

import java.util.List;

/**
 * @author Daria Usova
 */
@Mapper
public interface DivisionMapper {

    DivisionDto toDto(Division division);

    List<DivisionDto> toDtoList(List<Division> divisionList);

}
