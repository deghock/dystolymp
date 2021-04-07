package ru.spbu.distolymp.repository.education;

import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.education.Grade;

import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public interface GradeRepository extends CrudRepository<Grade, Long> {

    List<Grade> findAllByOrderById();

}
