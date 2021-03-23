package ru.spbu.distolymp.repository.lists;

import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.lists.Grade;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface GradeRepository extends CrudRepository<Grade, Long> {

    List<Grade> findAllByDivisionId(Long divisionId);

    void deleteGradeByIdAndDivisionId(Long id, Long divisionId);

    Grade findByIdAndDivisionId(Long id, Long divisionId);

}
