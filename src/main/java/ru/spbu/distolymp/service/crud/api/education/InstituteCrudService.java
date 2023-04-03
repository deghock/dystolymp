package ru.spbu.distolymp.service.crud.api.education;

import ru.spbu.distolymp.dto.entity.education.InstituteDto;

import java.util.List;

/**
 * @author Maxim Andreev
 */
public interface InstituteCrudService {

    List<InstituteDto> getAllInstitutes();

    void saveOrUpdateInstitute(InstituteDto instituteDto);

    InstituteDto getInstituteById(Long id);

    void deleteInstitutesById(List<Long> idList);

}
