package ru.spbu.distolymp.service.crud.api.division;

import ru.spbu.distolymp.dto.entity.division.DivisionDto;
import ru.spbu.distolymp.entity.division.Division;

import java.util.List;

/**
 * @author Daria Usova
 */
public interface DivisionCrudService {

    List<DivisionDto> getDivisionList();

    Division getAnyDivision();

}
