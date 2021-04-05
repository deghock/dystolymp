package ru.spbu.distolymp.service.crud.api.lists;

import ru.spbu.distolymp.dto.entity.lists.DivisionDto;
import ru.spbu.distolymp.entity.lists.Division;

import java.util.List;

/**
 * @author Daria Usova
 */
public interface DivisionCrudService {

    List<DivisionDto> getDivisionList();

    Division getAnyDivision();

}
