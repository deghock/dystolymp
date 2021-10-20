package ru.spbu.distolymp.repository.education;
import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.education.SchoolType;

import java.util.List;
/**
 * @author Maxim Andreev
 */
public interface SchoolTypeRepository extends CrudRepository<SchoolType, Long> {
    List<SchoolType> findAll();

    SchoolType getSchoolTypeById(Long id);
}