package ru.spbu.distolymp.service.crud.api.education;

import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.entity.education.SchoolDto;
import ru.spbu.distolymp.entity.education.School;


import java.util.List;

/**
 * @author Maxim Andreev
 */
public interface SchoolCrudService {

    List<SchoolDto> getAllSchools();

    SchoolDto getSchoolById(Long id);

    void deleteSchoolsById(List<Long> idList);

    List<SchoolDto> getSchools(Specification<School> specs);

    void save(SchoolDto schoolDto);

    void update(SchoolDto schoolDto);

    void saveOrUpdate(SchoolDto schoolDto);
}
