package ru.spbu.distolymp.repository.education;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.spbu.distolymp.entity.education.School;

import java.util.List;
import java.util.Optional;

/**
 * @author Maxim Andreev
 */
public interface SchoolRepository extends PagingAndSortingRepository<School, Long>,
        JpaSpecificationExecutor<School> {
    List<School> findAll();

    void deleteSchoolsByIdIn(List<Long> idList);
}
