package ru.spbu.distolymp.service.crud.api.education;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.spbu.distolymp.dto.admin.directories.schools.CreateSchoolDto;
import ru.spbu.distolymp.dto.entity.education.SchoolDto;
import ru.spbu.distolymp.dto.entity.geography.country.CountryDto;
import ru.spbu.distolymp.entity.education.School;
import ru.spbu.distolymp.entity.geography.Country;


import java.util.List;

/**
 * @author Maxim Andreev
 */
public interface SchoolCrudService {

    List<SchoolDto> getSchools(Sort sort);

    List<SchoolDto> getSchools(Pageable pageable);

    List<SchoolDto> getSchools(Specification<School> specs, Sort sort);

    List<SchoolDto> getSchools(Specification<School> specs, Pageable pageable);

    SchoolDto getSchoolById(Long id);

    void deleteSchoolsById(List<Long> idList);

    void save(SchoolDto schoolDto);

    void update(SchoolDto schoolDto);

    void saveOrUpdate(SchoolDto schoolDto);
}
