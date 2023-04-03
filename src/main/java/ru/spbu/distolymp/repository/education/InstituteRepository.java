package ru.spbu.distolymp.repository.education;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.spbu.distolymp.entity.education.Institute;


import java.util.List;
import java.util.Optional;

/**
 * @author Maxim Andreev
 */
public interface InstituteRepository extends CrudRepository<Institute, Long> {

    List<Institute> findAllByOrderByOrder();

    @Query("select coalesce(max(i.order), 0) from Institute i")
    Integer findMaxOrder();

    List<Institute> findByOrderBetween(Integer startOrder, Integer endOrder);

    void deleteInstitutesByIdIn(List<Long> idList);

    Optional<Institute> findByOrder(Integer order);
}
